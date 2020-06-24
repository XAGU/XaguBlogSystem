package com.xagu.blog.utils;

/**
 * @author xagu
 * Created on 2020/6/23
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface Constants {

    Integer DEFAULT_SIZE = 30;
    Integer DEFAULT_PAGE = 1;

    interface User {
        String ROLE_ADMIN = "role_admin";
        String ROLE_NORMAL = "role_normal";
        String DEFAULT_AVATAR = "https://cdn.sunofbeaches.com/images/default_avatar.png";
        String DEFAULT_STATUS = "1";
        String KEY_CAPTCHA_CONTENT = "key_captcha_content_";
        //验证码有效时间
        Integer CAPTCHA_TIME = 60 * 10;
        String KEY_EMAIL_SEND_IP = "key_email_send_ip_";
        //验证码IP发送次数限制
        Integer KEY_EMAIL_SEND_IP_LIMIT_TIMES = 10;
        //验证码IP发送时间限制
        Integer KEY_EMAIL_SEND_IP_LIMIT = 60 * 60;
        String KEY_EMAIL_SEND_ADDRESS = "key_email_send_address_";
        String KEY_EMAIL_CODE_CONTENT = "key_email_code_content_";
        Integer KEY_EMAIL_SEND_ADDRESS_LIMIT = 30;
    }

    interface Settings {
        String MANAGER_ACCOUNT_INIT_STATE = "manager_account_init_state";
    }
}
