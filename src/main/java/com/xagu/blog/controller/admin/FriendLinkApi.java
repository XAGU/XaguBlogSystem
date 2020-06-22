package com.xagu.blog.controller.admin;

import com.xagu.blog.pojo.FriendLink;
import com.xagu.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author xagu
 * Created on 2020/6/22
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@RestController
@RequestMapping("admin/friend_link")
public class FriendLinkApi {

    /**
     * 添加友链
     *
     * @return
     */
    @PostMapping
    public ResponseResult addFriendLink(@RequestBody FriendLink friendLink) {
        return null;
    }

    /**
     * 删除友链
     *
     * @return
     */
    @DeleteMapping("{friendLinkId}")
    public ResponseResult deleteFriendLink(@PathVariable("friendLinkId") String friendLinkId) {
        return null;
    }

    /**
     * 修改友链
     *
     * @return
     */
    @PostMapping("{friendLinkId}")
    public ResponseResult updateFriendLink(@PathVariable("friendLinkId") String friendLinkId, @RequestBody FriendLink friendLink) {
        return null;
    }


    /**
     * 根据Id查询友链
     *
     * @return
     */
    @GetMapping("{friendLinkId}")
    public ResponseResult addFriendLink(@PathVariable("friendLinkId") String friendLinkId, @RequestBody FriendLink friendLink) {
        return null;
    }

    /**
     * 查询所有分类
     *
     * @return
     */
    @GetMapping("list")
    public ResponseResult listFriendLink(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return null;
    }

}
