package com.xagu.blog.controller.portal;

import com.xagu.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author xagu
 * Created on 2020/6/22
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@RestController
@RequestMapping("portal/web_site_info")
public class WebSiteInfoPortalApi {


    /**
     * 获取文章分类
     *
     * @return
     */
    @GetMapping("categories")
    public ResponseResult getCategories() {
        return null;
    }


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
     * 获取seo信息
     *
     * @return
     */
    @GetMapping("seo")
    public ResponseResult getSeoInfo() {
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

    /**
     * 获取轮播图
     * @return
     */
    @GetMapping("loop")
    public ResponseResult getLoops(){
        return null;
    }

    /**
     * 获取网站友链
     * @return
     */
    @GetMapping("friend_link")
    public ResponseResult getFriendLink(){
        return null;
    }
}
