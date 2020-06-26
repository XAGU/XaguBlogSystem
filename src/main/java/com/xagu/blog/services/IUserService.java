package com.xagu.blog.services;

import com.xagu.blog.pojo.User;
import com.xagu.blog.response.ResponseResult;

/**
 * @author xagu
 * Created on 2020/6/22
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface IUserService {
    /**
     * 初始化管理员
     *
     * @param user
     * @return
     */
    ResponseResult initManagerAccount(User user);

    /**
     * 获取验证码
     *
     * @param captchaKey
     * @throws Exception
     */
    void createCaptcha(String captchaKey) throws Exception;

    /**
     * 发送邮件
     *
     * @param email
     * @return
     */
    ResponseResult sendEmail(String type, String email);

    /**
     * 注册
     *
     * @param user
     * @param emailCode
     * @param captchaCode
     * @param captchaKey
     * @return
     */
    ResponseResult register(User user, String emailCode, String captchaCode, String captchaKey);

    /**
     * 登录
     * @param captchaKey
     * @param captcha
     * @param user
     * @return
     */
    ResponseResult doLogin(String captchaKey, String captcha, User user);

    /**
     * 检查当前用户
     * @return
     */
    User checkUser();

    /**
     * 根据id查询用户信息
     * @param userId
     * @return
     */
    ResponseResult getUserInfoById(String userId);

    /**
     * 检查邮箱
     * @param email
     * @return
     */
    ResponseResult checkEmail(String email);

    /**
     * 检查用户名
     * @param userName
     * @return
     */
    ResponseResult checkUserName(String userName);

    /**
     * 修改用户信息
     * @param userId
     * @param user
     * @return
     */
    ResponseResult updateUserInfo(String userId, User user);

    /**
     * 删除账户通过id,不是真的删除，修改状态而已
     * @param userId
     * @return
     */
    ResponseResult deleteByUserId(String userId);

    /**
     * 分页查询user
     * @param page
     * @param size
     * @return
     */
    ResponseResult listUsers(Integer page, Integer size);

    /**
     * 更新密码
     * @param verifyCode
     * @param user
     * @return
     */
    ResponseResult updateUserPassword(String verifyCode, User user);

    /**
     * 修改邮箱
     * @param email
     * @param verifyCode
     * @return
     */
    ResponseResult updateUserEmail(String email, String verifyCode);

    /**
     * 退出登录
     * @return
     */
    ResponseResult doLogout();
}
