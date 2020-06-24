package com.xagu.blog.services;

import com.xagu.blog.pojo.User;
import com.xagu.blog.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
     * @param request
     * @return
     */
    ResponseResult initManagerAccount(User user, HttpServletRequest request);

    /**
     * 获取验证码
     *
     * @param response
     * @param captchaKey
     * @throws Exception
     */
    void createCaptcha(HttpServletResponse response, String captchaKey) throws Exception;

    /**
     * 发送邮件
     *
     * @param request
     * @param email
     * @return
     */
    ResponseResult sendEmail(HttpServletRequest request, String type, String email);

    /**
     * 注册
     *
     * @param user
     * @param emailCode
     * @param captchaCode
     * @param captchaKey
     * @param request
     * @return
     */
    ResponseResult register(User user, String emailCode, String captchaCode, String captchaKey, HttpServletRequest request);
}
