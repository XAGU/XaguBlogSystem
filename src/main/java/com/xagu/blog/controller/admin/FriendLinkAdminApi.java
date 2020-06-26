package com.xagu.blog.controller.admin;

import com.xagu.blog.pojo.FriendLink;
import com.xagu.blog.response.ResponseResult;
import com.xagu.blog.services.IFriendLinkService;
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
@RequestMapping("admin/friend_link")
public class FriendLinkAdminApi {

    @Autowired
    IFriendLinkService friendLinkService;

    /**
     * 添加友链
     *
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @PostMapping
    public ResponseResult addFriendLink(@RequestBody FriendLink friendLink) {
        return friendLinkService.addFriendLink(friendLink);
    }

    /**
     * 删除友链
     *
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @DeleteMapping("{friendLinkId}")
    public ResponseResult deleteFriendLink(@PathVariable("friendLinkId") String friendLinkId) {
        return friendLinkService.deleteFriendLink(friendLinkId);
    }

    /**
     * 修改友链
     *
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @PutMapping("{friendLinkId}")
    public ResponseResult updateFriendLink(@PathVariable("friendLinkId") String friendLinkId, @RequestBody FriendLink friendLink) {
        return friendLinkService.updateFriendLink(friendLinkId, friendLink);
    }


    /**
     * 根据Id查询友链
     *
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("{friendLinkId}")
    public ResponseResult getFriendLink(@PathVariable("friendLinkId") String friendLinkId) {
        return friendLinkService.getFriendLink(friendLinkId);
    }

    /**
     * 查询所有分类
     *
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("list/{page}/{size}")
    public ResponseResult listFriendLink(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return friendLinkService.listFriendLink(page, size);
    }

}
