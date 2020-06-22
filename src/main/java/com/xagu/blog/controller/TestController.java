package com.xagu.blog.controller;

import com.xagu.blog.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
