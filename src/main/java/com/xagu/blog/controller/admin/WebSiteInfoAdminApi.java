package com.xagu.blog.controller.admin;

import com.xagu.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author xagu
 * Created on 2020/6/22
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@RestController
@RequestMapping("admin/web_site_info")
public class WebSiteInfoAdminApi {

    /**
     * 获取网址标题
     *
     * @return
     */
    @GetMapping("title")
    public ResponseResult getWebSiteInfo() {
        return null;
    }

    /**
     * 修改网站标题
     *
     * @param title
     * @return
     */
    @PutMapping("title")
    public ResponseResult updateWebSiteInfo(@RequestParam("title") String title) {
        return null;
    }

    /**
     * 获取seo信息
     *
     * @return
     */
    @GetMapping("seo")
    public ResponseResult getSeoInfo() {
        return null;
    }

    /**
     * 修改seo信息
     *
     * @return
     */
    @PutMapping("seo")
    public ResponseResult updateSeoInfo(@RequestParam("keywords") String keywords, @RequestParam("description") String description) {
        return null;
    }

    /**
     * 获取网站统计信息
     * @return
     */
    @GetMapping("view_count")
    public ResponseResult getWebSiteViewCount(){
        return null;
    }
}
