package com.xagu.blog.services.impl;

import com.xagu.blog.pojo.User;
import com.xagu.blog.services.IUserService;
import com.xagu.blog.utils.Constants;
import com.xagu.blog.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xagu
 * Created on 2020/6/25
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Service("permission")
public class PermissionService {

    @Autowired
    HttpServletResponse response;

    @Autowired
    HttpServletRequest request;

    @Autowired
    IUserService userService;

    /**
     * 是不是管理员
     *
     * @return
     */
    public boolean admin() {
        String tokenKey = CookieUtil.getCookie(request, Constants.User.XAGU_BLOG_TOKEN);
        //没有令牌的key
        if (StringUtils.isEmpty(tokenKey)) {
            return false;
        }
        User user = userService.checkUser();
        //没登陆
        if (user == null) {
            return false;
        }
        return Constants.User.ROLE_ADMIN.equals(user.getRoles());
    }
}
