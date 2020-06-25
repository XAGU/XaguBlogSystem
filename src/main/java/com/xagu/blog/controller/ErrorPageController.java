package com.xagu.blog.controller;

import com.xagu.blog.response.ResponseResult;
import com.xagu.blog.response.ResponseState;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xagu
 * Created on 2020/6/26
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@RestController
public class ErrorPageController {

    @GetMapping("403")
    public ResponseResult error403() {
        return ResponseResult.state(ResponseState.ERROR_403);
    }

    @GetMapping("404")
    public ResponseResult error404() {
        return ResponseResult.state(ResponseState.ERROR_404);
    }

    @GetMapping("504")
    public ResponseResult error504() {
        return ResponseResult.state(ResponseState.ERROR_504);
    }

    @GetMapping("505")
    public ResponseResult error505() {
        return ResponseResult.state(ResponseState.ERROR_505);
    }


}
