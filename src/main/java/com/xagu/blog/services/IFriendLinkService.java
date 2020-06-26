package com.xagu.blog.services;

import com.xagu.blog.pojo.FriendLink;
import com.xagu.blog.response.ResponseResult;

/**
 * @author xagu
 * Created on 2020/6/26
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface IFriendLinkService {
    /**
     * 添加友链
     * @param friendLink
     * @return
     */
    ResponseResult addFriendLink(FriendLink friendLink);

    /**
     * 获取友链
     * @param friendLinkId
     * @return
     */
    ResponseResult getFriendLink(String friendLinkId);

    /**
     * 分页查询所有
     * @param page
     * @param size
     * @return
     */
    ResponseResult listFriendLink(Integer page, Integer size);

    /**
     * 删除友链
     * @param friendLinkId
     * @return
     */
    ResponseResult deleteFriendLink(String friendLinkId);

    /**
     * 更新友链
     * @param friendLinkId
     * @param friendLink
     * @return
     */
    ResponseResult updateFriendLink(String friendLinkId, FriendLink friendLink);
}
