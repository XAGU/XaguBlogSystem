package com.xagu.blog.services.impl;

import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.xagu.blog.dao.RefreshTokenDao;
import com.xagu.blog.dao.SettingsDao;
import com.xagu.blog.dao.UserDao;
import com.xagu.blog.pojo.RefreshToken;
import com.xagu.blog.pojo.Setting;
import com.xagu.blog.pojo.User;
import com.xagu.blog.response.ResponseResult;
import com.xagu.blog.response.ResponseState;
import com.xagu.blog.services.IUserService;
import com.xagu.blog.utils.*;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * @author xagu
 * Created on 2020/6/22
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Slf4j
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserServiceImpl implements IUserService {

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SettingsDao settingsDao;

    @Autowired
    private RefreshTokenDao refreshTokenDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailUtil emailUtil;

    @Override
    public ResponseResult initManagerAccount(User user) {
        //检查是否有初始化
        Setting managerAccountState = settingsDao.findOneByKey(Constants.Settings.MANAGER_ACCOUNT_INIT_STATE);
        if (managerAccountState != null) {
            return ResponseResult.FAILED("管理员已经初始化");
        }
        //检查数据
        if (StringUtils.isEmpty(user.getUserName())) {
            return ResponseResult.FAILED("用户名不能为空");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            return ResponseResult.FAILED("密码不能为空");
        }
        if (StringUtils.isEmpty(user.getEmail())) {
            return ResponseResult.FAILED("邮箱不能为空");
        }
        //补充数据
        user.setId(snowFlake.nextId() + "");
        user.setRoles(Constants.User.ROLE_ADMIN);
        user.setAvatar(Constants.User.DEFAULT_AVATAR);
        user.setState(Constants.User.DEFAULT_STATUS);
        String ipAddr = IpUtil.getIpAddr(request);
        user.setRegIp(ipAddr);
        user.setLoginIp(ipAddr);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        //保存到数据库
        userDao.save(user);
        //更新已经添加的标记
        Setting managerAccountInitState = new Setting();
        managerAccountInitState.setId(snowFlake.nextId() + "");
        managerAccountInitState.setKey("manager_account_init_state");
        managerAccountInitState.setValue("1");
        managerAccountInitState.setCreateTime(new Date());
        managerAccountInitState.setUpdateTime(new Date());
        settingsDao.save(managerAccountInitState);
        return ResponseResult.SUCCESS("初始化成功");
    }

    @Autowired
    private Random random;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void createCaptcha(String captchaKey) throws Exception {
        if (StringUtils.isEmpty(captchaKey) || captchaKey.length() < 13) {
            return;
        }
        long key = 0L;
        try {
            key = Long.parseLong(captchaKey);
        } catch (Exception e) {
            return;
        }
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 三个参数分别为宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        // 设置字体
        // 有默认字体，可以不用设置
        specCaptcha.setFont(Captcha.FONT_1);
        // 设置类型，纯数字、纯字母、字母数字混合
        specCaptcha.setCharType(Captcha.TYPE_DEFAULT);

        String content = specCaptcha.text().toLowerCase();

        // 验证码存入Redis
        redisUtil.set(Constants.User.KEY_CAPTCHA_CONTENT + key, content, Constants.User.CAPTCHA_TIME);
        // 输出图片流
        specCaptcha.out(response.getOutputStream());
    }

    /**
     * 发送邮箱验证码
     * 使用场景
     * 注册（register）:如果已经注册，提示邮箱已注册
     * 找回密码（forget）：如果没有注册，提示邮箱不存在
     * 修改邮箱（update）(新的邮箱):如果已经注册，提示已注册
     *
     * @param type
     * @param email
     * @return
     */
    @Override
    public ResponseResult sendEmail(String type, String email) {
        if (email == null) {
            return ResponseResult.FAILED("邮箱地址不能为空！");
        }
        //根据类型，查询邮箱是否存在
        User oneByEmail = userDao.findOneByEmail(email);
        if ("register".equals(type) || "update".equals(type)) {
            if (oneByEmail != null) {
                //邮箱已经被注册
                return ResponseResult.FAILED("该邮箱已经注册！");
            }
        } else if ("forget".equals(type)) {
            if (oneByEmail == null) {
                //邮箱不存在
                return ResponseResult.FAILED("邮箱不存在！");
            }
        }
        //防止暴力发送：同一个邮箱，间隔不少于30s，同一个ip。一小时最多只能发10次
        String ip = IpUtil.getIpAddr(request);
        //先从redis拿该ip的记录
        Integer ipSendTimes = (Integer) redisUtil.get(Constants.User.KEY_EMAIL_SEND_IP + ip);
        if (ipSendTimes != null && ipSendTimes > Constants.User.KEY_EMAIL_SEND_IP_LIMIT_TIMES) {
            return ResponseResult.FAILED("今日发送验证码次数已用尽！");
        }
        //先从redis拿该ip的记录
        if (redisUtil.get(Constants.User.KEY_EMAIL_SEND_ADDRESS + email) != null) {
            return ResponseResult.FAILED("发送验证码太频繁！");
        }
        //检查邮箱地址是否正确
        if (!TextUtil.isEmailAddress(email)) {
            return ResponseResult.FAILED("邮箱地址不正确！");
        }
        //发送验证码,6位数，100000---999999
        Integer code = random.nextInt(999999);
        //0----999999
        if (code < 100000) {
            code += 100000;
        }
        log.debug("发送注册验证码：" + email + "-->" + code);
        try {
            emailUtil.sendRegisterEmailVerifyCode(email, code);
        } catch (MailException e) {
            log.debug("发送注册验证码失败：" + email);
            return ResponseResult.FAILED("邮件发送失败！");
        }
        //做记录
        //发送记录
        if (ipSendTimes == null) {
            ipSendTimes = 0;
        }
        redisUtil.set(Constants.User.KEY_EMAIL_SEND_IP + ip, ++ipSendTimes, Constants.User.KEY_EMAIL_SEND_IP_LIMIT);
        redisUtil.set(Constants.User.KEY_EMAIL_SEND_ADDRESS + email, "SendRate", Constants.User.KEY_EMAIL_SEND_ADDRESS_LIMIT);
        //保存code
        redisUtil.set(Constants.User.KEY_EMAIL_CODE_CONTENT + email, String.valueOf(code), Constants.User.CAPTCHA_TIME);
        return ResponseResult.SUCCESS("邮箱验证码发送成功！");
    }

    @Override
    public ResponseResult register(User user, String emailCode, String captchaCode, String captchaKey) {
        //检查当前用户名是否已经注册
        String userName = user.getUserName();
        if (StringUtils.isEmpty(userName)) {
            return ResponseResult.FAILED("用户名不能为空");
        }
        User oneByUserName = userDao.findOneByUserName(userName);
        if (oneByUserName != null) {
            return ResponseResult.FAILED("用户名已经存在！");
        }
        //检测邮箱格式是否正确
        String email = user.getEmail();
        if (email == null) {
            return ResponseResult.FAILED("邮箱不能为空！");
        }
        if (!TextUtil.isEmailAddress(email)) {
            return ResponseResult.FAILED("邮箱格式错误！");
        }
        //检查该邮箱是否已经注册
        User oneByEmail = userDao.findOneByEmail(email);
        if (oneByEmail != null) {
            return ResponseResult.FAILED("该邮箱已经注册！");
        }
        //检查邮箱验证码是否正确
        String emailVerifyCode = (String) redisUtil.get(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
        if (emailVerifyCode == null) {
            return ResponseResult.FAILED("邮箱验证码无效！");
        }
        if (!emailVerifyCode.equals(emailCode)) {
            return ResponseResult.FAILED("邮箱验证码错误！");
        }
        //检查图灵验证码是否正确
        String captchaVerifyCode = (String) redisUtil.get(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        if (captchaVerifyCode == null) {
            return ResponseResult.FAILED("图形验证码无效！");
        }
        captchaVerifyCode = captchaVerifyCode.toLowerCase();
        captchaCode = captchaCode.toLowerCase();
        if (!captchaVerifyCode.equals(captchaCode)) {
            return ResponseResult.FAILED("图形验证码错误！");
        }
        //达到可以注册的条件
        //对密码进行加密
        String password = user.getPassword();
        if (password == null) {
            return ResponseResult.FAILED("密码不能为空");
        }
        user.setPassword(bCryptPasswordEncoder.encode(password));
        //补全数据，ID,IP,时间,角色，头像
        user.setId(snowFlake.nextId() + "");
        user.setRoles(Constants.User.ROLE_NORMAL);
        user.setAvatar(Constants.User.DEFAULT_AVATAR);
        user.setState(Constants.User.DEFAULT_STATUS);
        String ipAddr = IpUtil.getIpAddr(request);
        user.setRegIp(ipAddr);
        user.setLoginIp(ipAddr);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        //保存到数据库
        userDao.save(user);
        //干掉redis中的验证码
        //正确，干掉redis
        redisUtil.del(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
        //正确，干掉redis
        redisUtil.del(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        //返回结果
        return ResponseResult.state(ResponseState.REGISTER_SUCCESS);
    }

    @Override
    public ResponseResult doLogin(String captchaKey, String captcha, User user) {
        //检查图形验证码
        if (captcha == null) {
            return ResponseResult.FAILED("验证码不能为空！");
        }
        captcha = captcha.toLowerCase();
        String realCaptcha = (String) redisUtil.get(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        if (realCaptcha == null || !realCaptcha.equals(captcha)) {
            return ResponseResult.FAILED("验证码错误！");
        }
        //验证码正确就干掉redis
        redisUtil.del(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        //通过用户名查找用户
        String userName = user.getUserName();
        if (StringUtils.isEmpty(userName)) {
            return ResponseResult.FAILED("用户名不能为空！");
        }
        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            return ResponseResult.FAILED("密码不能为空！");
        }
        User dbUser = userDao.findOneByUserName(userName);
        if (dbUser == null) {
            dbUser = userDao.findOneByEmail(userName);
            if (dbUser == null) {
                return ResponseResult.FAILED("用户名或密码错误！");
            }
        }
        //拿到了数据库里的user
        if (!bCryptPasswordEncoder.matches(password, dbUser.getPassword())) {
            //登录失败
            return ResponseResult.FAILED("用户名或密码错误！");
        }
        //判断用户状态
        if (!"1".equals(dbUser.getState())) {
            return ResponseResult.state(ResponseState.ACCOUNT_DENIED);
        }
        //登录成功
        //jwt生成Token
        createToken(dbUser);
        return ResponseResult.state(ResponseState.LOGIN_SUCCESS);
    }

    /**
     * 创建token并保存到redis，同时生成refreshToken
     *
     * @param dbUser
     * @return 返回token的key
     */
    private String createToken(User dbUser) {
        //删除refreshToken
        refreshTokenDao.deleteByUserId(dbUser.getId());
        Map<String, Object> claims = ClaimsUtil.userToClaims(dbUser);
        //有效期2hours
        String token = JwtUtil.createToken(claims);
        //token的md5作为key
        String md5Token = DigestUtils.md5DigestAsHex(token.getBytes());
        //保存到redis
        redisUtil.set(Constants.User.KEY_JWT_TOKEN + md5Token, token, Constants.TimeValueInSecond.HOUR_2);
        //保存到cookie
        CookieUtil.setUpCookie(response, Constants.User.XAGU_BLOG_TOKEN, md5Token);
        //生成refreshToken
        String refreshToken = JwtUtil.createRefreshToken(dbUser.getId(), Constants.TimeValueInMillion.MONTH);
        //保存在数据库
        RefreshToken refreshTokenBean = new RefreshToken();
        refreshTokenBean.setId(String.valueOf(snowFlake.nextId()));
        refreshTokenBean.setRefreshToken(refreshToken);
        refreshTokenBean.setTokenKey(md5Token);
        refreshTokenBean.setUserId(dbUser.getId());
        refreshTokenBean.setCreateTime(new Date());
        refreshTokenBean.setUpdateTime(new Date());
        refreshTokenDao.save(refreshTokenBean);
        return md5Token;
    }

    @Override
    public User checkUser() {
        String key = CookieUtil.getCookie(request, Constants.User.XAGU_BLOG_TOKEN);
        User user = parseToUserByTokenKey(key);
        if (user == null) {
            //解析出错，过期了，
            //1、去数据库查
            RefreshToken refreshToken = refreshTokenDao.findOneByTokenKey(key);
            //2、不存在，未登录
            if (refreshToken == null) {
                return null;
            }
            //3、存在，解析refreshToken
            try {
                JwtUtil.parseJWT(refreshToken.getRefreshToken());
                //5、如果解析refreshToken成功，生成新的token
                String userId = refreshToken.getUserId();
                User dbUser = userDao.findOneById(userId);
                String tokenKey = createToken(dbUser);
                return parseToUserByTokenKey(tokenKey);
            } catch (Exception ex) {
                //4、如果解析refreshToken过期，没有登录
                return null;
            }
        }
        return user;
    }

    @Override
    public ResponseResult getUserInfoById(String userId) {
        //从数据库获取
        User dbUser = userDao.findOneById(userId);
        if (dbUser == null) {
            //不存在，return
            return ResponseResult.FAILED("用户不存在！");
        }
        //存在,复制对象，清空密码，email,ip,
        User user = new User();
        BeanUtils.copyProperties(dbUser, user);
        user.setPassword(null);
        user.setEmail(null);
        user.setLoginIp(null);
        user.setRegIp(null);
        user.setCreateTime(null);
        user.setUpdateTime(null);
        user.setState(null);
        //返回结果
        return ResponseResult.SUCCESS("获取成功！").setData(user);
    }

    @Override
    public ResponseResult checkEmail(String email) {
        return ResponseResult.decide(userDao.findOneByEmail(email) == null,
                "该邮箱可用！",
                "该邮箱不可用！");
    }

    @Override
    public ResponseResult checkUserName(String userName) {
        return ResponseResult.decide(userDao.findOneByUserName(userName) == null,
                "该用户名可用！",
                "该用户名不可用");
    }

    @Override
    public ResponseResult updateUserInfo(String userId, User user) {
        User userAccountFromKey = checkUser();
        if (userAccountFromKey == null) {
            return ResponseResult.state(ResponseState.ACCOUNT_NO_LOGIN);
        }
        //判断用户id是否一致
        if (!userAccountFromKey.getId().equals(userId)) {
            return ResponseResult.state(ResponseState.ASSESS_DENIED);
        }
        User userAccount = userDao.findOneById(userId);
        //允许用户修改的内容
        //用户名
        String userName = user.getUserName();
        if (!StringUtils.isEmpty(userName)) {
            if (userDao.findOneByUserName(userName) == null) {
                userAccount.setUserName(userName);
            } else {
                return ResponseResult.FAILED("用户名重复！");
            }
        }
        //头像
        if (!StringUtils.isEmpty(user.getAvatar())) {
            userAccount.setAvatar(user.getAvatar());
        }
        //签名
        String sign = user.getSign();
        if (sign != null) {
            userAccount.setSign(sign);
        }
        //更新时间
        userAccount.setUpdateTime(new Date());
        //干掉redis里的token
        redisUtil.del(Constants.User.KEY_JWT_TOKEN + CookieUtil.getCookie(request, Constants.User.XAGU_BLOG_TOKEN));
        return ResponseResult.SUCCESS("用户信息更新成功！");
    }

    @Override
    public ResponseResult deleteByUserId(String userId) {
        User dbUser = userDao.findOneById(userId);
        if (dbUser == null) {
            return ResponseResult.FAILED("用户不存在，无法删除！");
        }
        dbUser.setState("0");
        return ResponseResult.SUCCESS("删除成功！");
    }

    @Override
    public ResponseResult listUsers(Integer page, Integer size) {
        //分页查询
        if (page < Constants.Page.DEFAULT_PAGE) {
            page = Constants.Page.DEFAULT_PAGE;
        }
        //最少查5个
        if (size < Constants.Page.MIN_SIZE) {
            size = Constants.Page.MIN_SIZE;
        }
        //根据日期来排序
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return ResponseResult.SUCCESS("获取用户列表成功！").setData(userDao.listAllUserNoPassword(pageable));
    }

    @Override
    public ResponseResult updateUserPassword(String verifyCode, User user) {
        //检查邮箱
        String email = user.getEmail();
        if (StringUtils.isEmpty(email)) {
            return ResponseResult.FAILED("邮箱不能为空！");
        }
        //检查密码
        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            return ResponseResult.FAILED("密码不能为空！");
        }
        //根据邮箱去redis拿验证码
        String emailCode = (String) redisUtil.get(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
        //拿到了验证码开始对比
        if (StringUtils.isEmpty(emailCode) || !emailCode.equals(verifyCode)) {
            return ResponseResult.FAILED("邮箱验证码错误！");
        }
        //删除掉验证码
        redisUtil.del(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
        //验证码正确，开始修改密码
        Integer result = userDao.updatePasswordByEmail(bCryptPasswordEncoder.encode(password), email);
        return ResponseResult.decide(result > 0,
                "修改用户密码成功！",
                "修改用户密码失败！");
    }

    @Override
    public ResponseResult updateUserEmail(String email, String verifyCode) {
        //检查用户是否登录
        User currentUser = this.checkUser();
        if (currentUser == null) {
            return ResponseResult.state(ResponseState.ACCOUNT_NO_LOGIN);
        }
        //判断邮箱正确性
        if (StringUtils.isEmpty(email)) {
            return ResponseResult.FAILED("邮箱不能为空！");
        }
        //判断验证码
        String emailCode = (String) redisUtil.get(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
        if (StringUtils.isEmpty(verifyCode) || !verifyCode.equals(emailCode)) {
            return ResponseResult.FAILED("邮箱验证码错误！");
        }
        //删除掉验证码
        redisUtil.del(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
        //验证通过修改邮箱
        Integer result = userDao.updateEmailById(email, currentUser.getId());
        return ResponseResult.decide(result > 0,
                "修改用户邮箱成功！",
                "修改用户邮箱失败！");
    }

    @Override
    public ResponseResult doLogout() {
        //cookie里拿tokenkey
        String tokenKey = CookieUtil.getCookie(request, Constants.User.XAGU_BLOG_TOKEN);
        if (StringUtils.isEmpty(tokenKey)) {
            return ResponseResult.state(ResponseState.ACCOUNT_NO_LOGIN);
        }
        //删除mysql的refreshToken
        refreshTokenDao.deleteByTokenKey(tokenKey);
        //删除redis里的token
        redisUtil.del(Constants.User.KEY_JWT_TOKEN + tokenKey);
        //删除cookie的tokenKey
        CookieUtil.deleteCookie(response, Constants.User.XAGU_BLOG_TOKEN);
        return ResponseResult.SUCCESS("退出登录成功！");
    }

    private User parseToUserByTokenKey(String tokenKey) {
        String token = (String) redisUtil.get(Constants.User.KEY_JWT_TOKEN + tokenKey);
        if (token != null) {
            try {
                Claims claims = JwtUtil.parseJWT(token);
                User currentUser = ClaimsUtil.claimsToUser(claims);
                return currentUser;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
