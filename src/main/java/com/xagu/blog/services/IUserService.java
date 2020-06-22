package com.xagu.blog.services;

import com.xagu.blog.pojo.User;
import com.xagu.blog.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xagu
 * Created on 2020/6/22
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface IUserService {
    /**
     * 初始化管理员
     * @param user
     * @param request
     * @return
     */
    ResponseResult initManagerAccount(User user, HttpServletRequest request);
}
