package com.xagu.blog.controller.portal;

import com.xagu.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xagu
 * Created on 2020/6/22
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@RestController
@RequestMapping("/portal/search")
public class SearchPortalApi {

    @GetMapping
    public ResponseResult doSearch(@RequestParam("keyword") String keyword, @RequestParam("page") String page, @RequestParam("size") String size) {
        return null;
    }
}
