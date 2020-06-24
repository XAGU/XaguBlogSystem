package com.xagu.blog.controller;

import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.xagu.blog.response.ResponseResult;
import com.xagu.blog.utils.Constants;
import com.xagu.blog.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.Random;


/**
 * @author xagu
 * Created on 2020/6/22
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Slf4j
@RestController
public class TestController {

    @GetMapping
    public ResponseResult test() {
        log.info("aaaaa");
        return ResponseResult.SUCCESS().setData("hello world");
    }

    @Autowired
    RedisUtil redisUtil;

    @RequestMapping("/captcha")
    public void captcha(HttpServletResponse response) throws Exception {
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 三个参数分别为宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        // 设置字体
        // 有默认字体，可以不用设置
        specCaptcha.setFont(Captcha.FONT_1);
        // 设置类型，纯数字、纯字母、字母数字混合
        specCaptcha.setCharType(Captcha.TYPE_DEFAULT);

        String content = specCaptcha.text().toLowerCase();

        // 验证码存入Redis
        redisUtil.set(Constants.User.KEY_CAPTCHA_CONTENT + new Random().nextInt(), content, Constants.User.CAPTCHA_TIME);
        // 输出图片流
        specCaptcha.out(response.getOutputStream());
    }
}
