package com.xagu.blog.services;

import com.xagu.blog.response.ResponseResult;

/**
 * @author xagu
 * Created on 2020/7/1
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface IWebSiteInfoService {
    /**
     * 获取title
     * @return
     */
    ResponseResult getWebSiteTitle();

    /**
     * 更新title信息
     * @param title
     * @return
     */
    ResponseResult updateWebSiteInfo(String title);

    /**
     * 获取seo信息
     * @return
     */
    ResponseResult getSeoInfo();

    /**
     * 更新seo信息
     * @param keywords
     * @param description
     * @return
     */
    ResponseResult updateSeoInfo(String keywords, String description);

    /**
     * 获取网站统计信息
     * @return
     */
    ResponseResult getWebSiteViewCount();

}
