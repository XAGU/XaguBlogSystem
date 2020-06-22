package com.xagu.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author xagu
 * Created on 2020/6/22
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@EnableSwagger2
@SpringBootApplication
public class XaguBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(XaguBlogApplication.class, args);
    }
}
