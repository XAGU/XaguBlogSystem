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
@RequestMapping("portal/article")
public class ArticlePortalApi {

    /**
     * 分页查询所有文章
     */
    @GetMapping("list/{page}/{size}")
    public ResponseResult listArticle(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return null;
    }

    /**
     * 根据分类查询文章
     */
    @GetMapping("list/{categoryId}/{page}/{size}")
    public ResponseResult listArticleByCategory(@PathVariable("categoryId") String categoryId, @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return null;
    }

    @GetMapping("{articleId}")
    public ResponseResult getArticleDetail(@PathVariable("articleId") String articleId) {
        return null;
    }

    @GetMapping("recommend/{articleId}")
    public ResponseResult getRecommendArticles(@PathVariable("articleId") String articleId) {
        return null;
    }
}
