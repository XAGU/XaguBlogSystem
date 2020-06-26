package com.xagu.blog.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author xagu
 * Created on 2020/6/24
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    private String email;

    @Async
    public void sendRegisterEmailVerifyCode(String emailAddress, Integer code) throws MailException {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("XAGU的个人博客<" + email + ">");
        simpleMailMessage.setTo(emailAddress);
        simpleMailMessage.setSubject("XAGU的个人博客注册验证码");
        simpleMailMessage.setText("您的注册验证码为：" + code + "有效期为10分钟，若非本人操作，请忽略此邮件。");
        javaMailSender.send(simpleMailMessage);
    }
}
