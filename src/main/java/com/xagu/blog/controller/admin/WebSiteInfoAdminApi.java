package com.xagu.blog.controller.admin;

import com.xagu.blog.response.ResponseResult;
import com.xagu.blog.services.IWebSiteInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private IWebSiteInfoService webSiteInfoService;

    /**
     * 获取网址标题
     *
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("title")
    public ResponseResult getWebSiteTitle() {
        return webSiteInfoService.getWebSiteTitle();
    }

    /**
     * 修改网站标题
     *
     * @param title
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @PutMapping("title")
    public ResponseResult updateWebSiteInfo(@RequestParam("title") String title) {
        return webSiteInfoService.updateWebSiteInfo(title);
    }

    /**
     * 获取seo信息
     *
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("seo")
    public ResponseResult getSeoInfo() {
        return webSiteInfoService.getSeoInfo();
    }

    /**
     * 修改seo信息
     *
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @PutMapping("seo")
    public ResponseResult updateSeoInfo(@RequestParam("keywords") String keywords, @RequestParam("description") String description) {
        return webSiteInfoService.updateSeoInfo(keywords, description);
    }

    /**
     * 获取网站统计信息
     *
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("view_count")
    public ResponseResult getWebSiteViewCount() {
        return webSiteInfoService.getWebSiteViewCount();
    }
}
