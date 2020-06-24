package com.xagu.blog;

import com.xagu.blog.utils.RedisUtil;
import com.xagu.blog.utils.SnowFlake;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Random;

/**
 * @author xagu
 * Created on 2020/6/22
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@EnableAsync
@EnableSwagger2
@SpringBootApplication
public class XaguBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(XaguBlogApplication.class, args);
    }

    @Bean
    public Random createRandom() {
        return new Random();
    }
}
