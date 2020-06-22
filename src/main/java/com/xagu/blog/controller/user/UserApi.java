package com.xagu.blog.controller.user;

import com.xagu.blog.pojo.User;
import com.xagu.blog.response.ResponseResult;
import com.xagu.blog.services.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public ResponseResult initManagerAccount(@RequestBody User user, HttpServletRequest request) {
        log.info("username ===>" + user.getUserName());
        log.info("password ===>" + user.getPassword());
        log.info("email ===>" + user.getEmail());
        return ResponseResult.SUCCESS().setData(userService.initManagerAccount(user, request));
    }

    /**
     * 注册
     *
     * @return
     */
    @PostMapping
    public ResponseResult register(@RequestBody User user) {
        log.info("username ===>" + user.getUserName());
        log.info("password ===>" + user.getPassword());
        log.info("email ===>" + user.getEmail());
        return ResponseResult.SUCCESS().setData(user);
    }

    /**
     * 注册
     *
     * @return
     */
    @PostMapping("{captcha}")
    public ResponseResult login(@PathVariable("captcha") String captcha, @RequestBody User user) {
        log.info("username ===>" + user.getUserName());
        log.info("password ===>" + user.getPassword());
        log.info("email ===>" + user.getEmail());
        log.info("captcha ===>" + captcha);
        return ResponseResult.SUCCESS().setData(user);
    }

    /**
     * 获取图灵验证码
     *
     * @return
     */
    @GetMapping("/captcha")
    public ResponseResult getCaptcha() {
        return null;
    }

    /**
     * @return
     */
    @GetMapping("/verify_code")
    public ResponseResult sendVerifyCode(@RequestParam("email") String email) {
        log.info("email ===>" + email);
        return null;
    }

    /**
     * 更新密码
     *
     * @return
     */
    @PutMapping("password/{userId}")
    public ResponseResult updatePassword(@PathVariable("userId") String userId, @RequestBody User user) {
        return null;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseResult getUserInfo(@PathVariable("userId") String userId) {
        return null;
    }

    /**
     * 修改用户信息
     *
     * @return
     */
    @PutMapping("/{userId}")
    public ResponseResult updateUserInfo(@PathVariable("userId") String userId, @RequestBody User user) {
        return null;
    }

    /**
     * 获取用户列表
     */
    @GetMapping("list")
    public ResponseResult listUser(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return null;
    }

    /**
     * 删除用户
     *
     * @return
     */
    @DeleteMapping("{userId}")
    public ResponseResult deleteUser(@PathVariable("userId") String userId) {
        return null;
    }


}
