package com.xagu.blog.controller.admin;

import com.xagu.blog.pojo.Article;
import com.xagu.blog.response.ResponseResult;
import com.xagu.blog.services.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author xagu
 * Created on 2020/6/22
 * Email:xagu_qc@foxmail.com
 * Describe: 文章的api
 */
@RestController
@RequestMapping("admin/article")
public class ArticleAdminApi {

    @Autowired
    private IArticleService articleService;

    /**
     * 添加文章
     */
    @PreAuthorize("@permission.admin()")
    @PostMapping
    public ResponseResult postArticle(@RequestBody Article article) {
        return articleService.postArticle(article);
    }

    /**
     * 删除文章
     */
    @PreAuthorize("@permission.admin()")
    @DeleteMapping("{articleId}")
    public ResponseResult deleteArticle(@PathVariable("articleId") String articleId) {
        return null;
    }

    /**
     * 修改文章
     */
    @PreAuthorize("@permission.admin()")
    @PutMapping("{articleId}")
    public ResponseResult updateArticle(@PathVariable("articleId") String articleId, @RequestBody Article article) {
        return null;
    }

    /**
     * 根据id查询文章
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("{articleId}")
    public ResponseResult getArticle(@PathVariable("looperId") String articleId) {
        return null;
    }

    /**
     * 查询所有文章
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("list/{page}/{size}")
    public ResponseResult listArticle(@PathVariable("page") Integer page,
                                      @PathVariable("size") Integer size,
                                      @RequestParam(value = "state", required = false) String state,
                                      @RequestParam(value = "keyword", required = false) String keyword,
                                      @RequestParam(value = "categoryId", required = false) String categoryId) {
        return articleService.listArticle(page, size, state, keyword, categoryId);
    }

    /**
     * 修改文章状态
     *
     * @param articleId
     * @param articleState
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @PutMapping("state/{articleId}/{articleState}")
    public ResponseResult updateArticleState(@PathVariable("articleId") String articleId, @PathVariable("articleState") String articleState) {
        return null;
    }

    /**
     * 置顶文章
     *
     * @param articleId
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @PutMapping("top/{articleId}")
    public ResponseResult topArticleState(@PathVariable("articleId") String articleId) {
        return null;
    }
}
