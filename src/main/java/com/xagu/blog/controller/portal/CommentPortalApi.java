package com.xagu.blog.controller.portal;

import com.xagu.blog.pojo.Comment;
import com.xagu.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author xagu
 * Created on 2020/6/22
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@RestController
@RequestMapping("portal/comment")
public class CommentPortalApi {
    /**
     * 回复
     */
    @PostMapping
    public ResponseResult postComment(@RequestBody Comment comment) {
        return null;
    }

    /**
     * 删除评论
     */
    @DeleteMapping("{commentId}")
    public ResponseResult deleteComment(@PathVariable("commentId") String commentId) {
        return null;
    }

    /**
     * 查询文章的评论
     */
    @GetMapping("list/{articleId}")
    public ResponseResult getCommentByArticleId(@PathVariable("articleId") String articleId) {
        return null;
    }
}
