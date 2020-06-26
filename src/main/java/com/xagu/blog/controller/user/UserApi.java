package com.xagu.blog.controller.user;

import com.xagu.blog.pojo.User;
import com.xagu.blog.response.ResponseResult;
import com.xagu.blog.services.IUserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xagu
 * Created on 2020/6/22
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserApi {

    @Autowired
    IUserService userService;

    /**
     * 初始化管理员账号
     *
     * @return
     */
    @PostMapping("admin_account")
    public ResponseResult initManagerAccount(@RequestBody User user) {
        log.debug("username ===>" + user.getUserName());
        log.debug("password ===>" + user.getPassword());
        log.debug("email ===>" + user.getEmail());
        return userService.initManagerAccount(user);
    }

    /**
     * 注册
     *
     * @return
     */
    @PostMapping("register")
    public ResponseResult register(@RequestBody User user,
                                   @RequestParam("email_code") String emailCode,
                                   @RequestParam("captcha_code") String captchaCode,
                                   @RequestParam("captcha_key") String captchaKey) {
        return userService.register(user, emailCode, captchaCode, captchaKey);
    }

    /**
     * 登录
     * 需要的数据
     * 账户 用户名或者是邮箱
     * 密码
     * 验证码
     * 验证码的key
     *
     * @param user
     * @param captcha
     * @param captchaKey
     * @return
     */
    @PostMapping("login/{captcha_key}/{captcha}")
    public ResponseResult login(@PathVariable("captcha_key") String captchaKey, @PathVariable("captcha") String captcha, @RequestBody User user) {
        return userService.doLogin(captchaKey, captcha, user);
    }


    /**
     * 获取图灵验证码
     *
     * @return
     */
    @GetMapping("/captcha")
    public void getCaptcha(@RequestParam("captcha_key") String captchaKey) {
        try {
            userService.createCaptcha(captchaKey);
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    /**
     * @return
     */
    @GetMapping("/verify_code")
    public ResponseResult sendVerifyCode(@RequestParam("type") String type, @RequestParam("email") String email) {
        log.debug("email ===>" + email);
        return userService.sendEmail(type, email);
    }

    /**
     * 修改密码Password
     * 修改密码
     * 普通做法：通过旧密码对比来更新密码
     * <p>
     * 找回密码/修改密码
     * 发送验证码到邮箱---》判断验证码是否正确来判断
     * 对应邮箱/手机所注册的号码是否属于你
     * <p>
     * 步骤
     * 1、用户填写邮箱
     * 2、用户获取验证码type=forget
     * 3、填写新的密码
     * 4、提交数据
     * <p>
     * 数据包括
     * <p>
     * 1、邮箱和新密码
     * 2、验证码
     * <p>
     * 如果验证码正确---》可以修改密码
     *
     * @return
     */
    @PutMapping("password/{verify_code}")
    public ResponseResult updatePassword(@PathVariable("verify_code") String verifyCode, @RequestBody User user) {
        return userService.updateUserPassword(verifyCode, user);
    }

    /**
     * 1、必须已经登录
     * 2、新的邮箱可用
     * <p>
     * 步骤
     * 1、用户填写新邮箱
     * 2、用户获取验证码type=update
     * 3、输入验证码
     * 4、提交数据
     * <p>
     * 数据包括
     * <p>
     * 1、新的邮箱地址
     * 2、验证码
     * 其他的数据从token拿
     *
     * <p>
     * 如果验证码正确---》可以修改
     *
     * @return
     */
    @PutMapping("email")
    public ResponseResult updateEmail(@RequestParam("email") String email, @RequestParam("verify_code") String verifyCode) {
        return userService.updateUserEmail(email, verifyCode);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("user_info/{userId}")
    public ResponseResult getUserInfo(@PathVariable("userId") String userId) {
        return userService.getUserInfoById(userId);
    }

    /**
     * 修改用户信息
     * 允许用户修改的内容
     * 头像
     * 用户名
     * 密码（单独接口）
     * 签名
     * email（单独接口）
     *
     * @return
     */
    @PutMapping("user_info/{userId}")
    public ResponseResult updateUserInfo(@PathVariable("userId") String userId, @RequestBody User user) {
        return userService.updateUserInfo(userId, user);
    }

    /**
     * 获取用户列表
     * 权限，管理员权限
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("list")
    public ResponseResult listUser(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return userService.listUsers(page, size);
    }

    /**
     * 删除用户
     *
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @DeleteMapping("{userId}")
    public ResponseResult deleteUser(@PathVariable("userId") String userId) {
        return userService.deleteByUserId(userId);
    }


    /**
     * 检查邮箱是否可用
     *
     * @param email
     * @return
     */
    @ApiResponses({
            @ApiResponse(code = 20000, message = "表示当前邮箱已经被注册！"),
            @ApiResponse(code = 40000, message = "表示当前邮箱未注册！")
    })
    @GetMapping("email_status")
    public ResponseResult checkEmail(@RequestParam("email") String email) {
        return userService.checkEmail(email);
    }

    /**
     * 检查用户名是否可用
     *
     * @param userName
     * @return
     */
    @ApiResponses({
            @ApiResponse(code = 20000, message = "表示当前用户名已经被注册！"),
            @ApiResponse(code = 40000, message = "表示当前用户名未注册！")
    })
    @GetMapping("username_status")
    public ResponseResult checkUserName(@RequestParam("userName") String userName) {
        return userService.checkUserName(userName);
    }

    /**
     * 退出登录
     * 拿到tokenKey
     * 删除redis中的token
     * 删除mysql里的refreshToken
     * 删除cookie的tokenKey
     *
     * @return
     */
    @GetMapping("logout")
    public ResponseResult logout() {
        return userService.doLogout();
    }

}
