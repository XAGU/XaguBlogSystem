package com.xagu.blog.controller.admin;

import com.xagu.blog.pojo.Article;
import com.xagu.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author xagu
 * Created on 2020/6/22
 * Email:xagu_qc@foxmail.com
 * Describe: 文章的api
 */
@RestController
@RequestMapping("admin/article")
public class ArticleApi {

    /**
     * 添加文章
     */
    @PostMapping
    public ResponseResult postArticle(@RequestBody Article article) {
        return null;
    }

    /**
     * 删除文章
     */
    @DeleteMapping("{articleId}")
    public ResponseResult deleteArticle(@PathVariable("articleId") String articleId) {
        return null;
    }

    /**
     * 修改文章
     */
    @PutMapping("{articleId}")
    public ResponseResult updateArticle(@PathVariable("articleId") String articleId, @RequestBody Article article) {
        return null;
    }

    /**
     * 根据id查询文章
     */
    @GetMapping("{articleId}")
    public ResponseResult getArticle(@PathVariable("looperId") String articleId) {
        return null;
    }

    /**
     * 查询所有文章
     */
    @GetMapping("list")
    public ResponseResult listArticle(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return null;
    }

    /**
     * 修改文章状态
     * @param articleId
     * @param articleState
     * @return
     */
    @PutMapping("state/{articleId}/{articleState}")
    public ResponseResult updateArticleState(@PathVariable("articleId") String articleId, @PathVariable("articleState") String articleState) {
        return null;
    }

    /**
     * 置顶文章
     * @param articleId
     * @return
     */
    @PutMapping("top/{articleId}")
    public ResponseResult topArticleState(@PathVariable("articleId") String articleId) {
        return null;
    }
}
