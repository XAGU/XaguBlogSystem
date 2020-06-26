package com.xagu.blog.utils;

import javax.validation.constraints.Min;

/**
 * @author xagu
 * Created on 2020/6/23
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface Constants {


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
        String KEY_JWT_TOKEN = "key_jwt_token_";
        String XAGU_BLOG_TOKEN = "xagu_blog_token";
    }


    interface Settings {
        String MANAGER_ACCOUNT_INIT_STATE = "manager_account_init_state";
    }

    interface Page {
        Integer DEFAULT_PAGE = 1;
        Integer MIN_SIZE = 5;
    }

    interface TimeValueInMillion {
        Integer MIN = 60 * 1000;
        Integer MIN_10 = MIN * 10 * 1000;
        Integer HOUR = 60 * MIN * 1000;
        Integer DAY = 24 * HOUR * 1000;
        Integer WEEK = 7 * DAY * 1000;
        Integer MONTH = 30 * DAY * 1000;
        Integer YEAR = 365 * DAY * 1000;
        Integer HOUR_2 = HOUR * 2 * 1000;
    }

    interface TimeValueInSecond {
        Integer MIN = 60;
        Integer MIN_10 = MIN * 10;
        Integer HOUR = 60 * MIN;
        Integer DAY = 24 * HOUR;
        Integer WEEK = 7 * DAY;
        Integer MONTH = 30 * DAY;
        Integer YEAR = 365 * DAY;
        Integer HOUR_2 = HOUR * 2;
    }
}
