package com.xagu.blog.services;

import com.xagu.blog.pojo.Article;
import com.xagu.blog.response.ResponseResult;

/**
 * @author xagu
 * Created on 2020/7/1
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface IArticleService {
    /**
     * 发布文章
     * @param article
     * @return
     */
    ResponseResult postArticle(Article article);

    /**
     * 分页查询文章
     * @param page
     * @param size
     * @param state
     * @param keyword
     * @param categoryId
     * @return
     */
    ResponseResult listArticle(Integer page, Integer size, String state, String keyword, String categoryId);
}
