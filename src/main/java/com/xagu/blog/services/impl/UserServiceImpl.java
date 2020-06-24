package com.xagu.blog.services.impl;

import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.xagu.blog.dao.SettingsDao;
import com.xagu.blog.dao.UserDao;
import com.xagu.blog.pojo.Setting;
import com.xagu.blog.pojo.User;
import com.xagu.blog.response.ResponseResult;
import com.xagu.blog.response.ResponseState;
import com.xagu.blog.services.IUserService;
import com.xagu.blog.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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
    private SnowFlake snowFlake;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SettingsDao settingsDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailUtil emailUtil;

    @Override
    public ResponseResult initManagerAccount(User user, HttpServletRequest request) {
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
    public void createCaptcha(HttpServletResponse response, String captchaKey) throws Exception {
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
     * @param request
     * @param type
     * @param email
     * @return
     */
    @Override
    public ResponseResult sendEmail(HttpServletRequest request, String type, String email) {
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
    public ResponseResult register(User user, String emailCode, String captchaCode, String captchaKey, HttpServletRequest request) {
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
}
