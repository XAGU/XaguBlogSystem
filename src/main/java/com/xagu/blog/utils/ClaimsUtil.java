package com.xagu.blog.utils;

import com.xagu.blog.pojo.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xagu
 * Created on 2020/6/25
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public class ClaimsUtil {

    public static final String ID = " id";
    public static final String USER_NAME = "user_name";
    public static final String ROLES = "roles";
    public static final String AVATAR = "avatar";
    public static final String EMAIL = "email";
    public static final String SIGN = "sign";

    public static Map<String, Object> userToClaims(User user) {
        Map<String, Object> claims = new HashMap<>(6);
        claims.put(ID, user.getId());
        claims.put(USER_NAME, user.getUserName());
        claims.put(ROLES, user.getRoles());
        claims.put(AVATAR, user.getAvatar());
        claims.put(EMAIL, user.getEmail());
        claims.put(SIGN, user.getSign());
        return claims;
    }

    public static User claimsToUser(Map<String, Object> claims) {
        User user = new User();
        user.setId((String) claims.get(ID));
        user.setUserName((String) claims.get(USER_NAME));
        user.setRoles((String) claims.get(ROLES));
        user.setAvatar((String) claims.get(AVATAR));
        user.setEmail((String) claims.get(EMAIL));
        user.setSign((String) claims.get(SIGN));
        return user;
    }
}
