package com.xagu.blog.controller.admin;

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
@RequestMapping("admin/comment")
public class CommentAdminApi {


    /**
     * 删除评论
     */
    @DeleteMapping("{commentId}")
    public ResponseResult deleteComment(@PathVariable("commentId") String commentId) {
        return null;
    }


    /**
     * 查询所有回复
     */
    @PutMapping("list")
    public ResponseResult listComment(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return null;
    }

    /**
     * 置顶回复
     */
    @PutMapping("top/{commentId}")
    public ResponseResult topComment(@PathVariable("commentId") String commentId) {
        return null;
    }
}
