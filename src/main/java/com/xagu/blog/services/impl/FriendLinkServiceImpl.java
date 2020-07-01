package com.xagu.blog.services.impl;

import com.xagu.blog.dao.FriendLinkDao;
import com.xagu.blog.pojo.FriendLink;
import com.xagu.blog.response.ResponseResult;
import com.xagu.blog.services.IFriendLinkService;
import com.xagu.blog.utils.Constants;
import com.xagu.blog.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author xagu
 * Created on 2020/6/26
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class FriendLinkServiceImpl extends BaseService implements IFriendLinkService {

    @Autowired
    private SnowFlake snowFlake;
    @Autowired
    private FriendLinkDao friendLinkDao;


    @Override
    public ResponseResult addFriendLink(FriendLink friendLink) {
        //判断数据
        String friendLinkUrl = friendLink.getUrl();
        if (StringUtils.isEmpty(friendLink)) {
            return ResponseResult.FAILED("友链链接不能为空！");
        }
        String friendLinkName = friendLink.getName();
        if (StringUtils.isEmpty(friendLinkName)) {
            return ResponseResult.FAILED("友链名称不能为空！");
        }
        String friendLinkLogo = friendLink.getLogo();
        if (StringUtils.isEmpty(friendLinkLogo)) {
            return ResponseResult.FAILED("友链LOGO不能为空！");
        }
        //补全数据
        friendLink.setId(snowFlake.nextId() + "");
        friendLink.setCreateTime(new Date());
        friendLink.setUpdateTime(new Date());
        //保存数据
        friendLinkDao.save(friendLink);
        //返回结果
        return ResponseResult.SUCCESS("添加友链成功");
    }

    @Override
    public ResponseResult getFriendLink(String friendLinkId) {
        FriendLink friendLink = friendLinkDao.findOneById(friendLinkId);
        return ResponseResult.decide(friendLink != null,
                "获取友链成功！",
                "获取友链失败！").setData(friendLink);
    }

    @Override
    public ResponseResult listFriendLink(Integer page, Integer size) {
        page = this.checkPage(page);
        size = this.checkSize(size);
        Sort sort = Sort.by(Sort.Direction.DESC, "order", "createTime");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<FriendLink> friendLinks = friendLinkDao.findAll(pageable);
        return ResponseResult.SUCCESS("查询友链成功！").setData(friendLinks);
    }

    @Override
    public ResponseResult deleteFriendLink(String friendLinkId) {
        Integer result = friendLinkDao.deleteByUpdateState(friendLinkId);
        return ResponseResult.decide(result > 0,
                "删除友链成功！",
                "删除友链失败！");
    }

    @Override
    public ResponseResult updateFriendLink(String friendLinkId, FriendLink friendLink) {
        //查询数据
        FriendLink dbFriendLink = friendLinkDao.findOneById(friendLinkId);
        if (dbFriendLink == null) {
            return ResponseResult.FAILED("友链不存在！");
        }
        //检查数据
        String friendLinkName = friendLink.getName();
        if (!StringUtils.isEmpty(friendLinkName)) {
            dbFriendLink.setName(friendLinkName);
        }
        String friendLinkLogo = friendLink.getLogo();
        if (!StringUtils.isEmpty(friendLinkLogo)) {
            dbFriendLink.setLogo(friendLinkLogo);
        }
        String friendLinkUrl = friendLink.getUrl();
        if (!StringUtils.isEmpty(friendLinkUrl)) {
            dbFriendLink.setUrl(friendLinkUrl);
        }
        Long friendLinkOrder = friendLink.getOrder();
        dbFriendLink.setOrder(friendLinkOrder);
        dbFriendLink.setUpdateTime(new Date());
        //保存数据
        friendLinkDao.save(dbFriendLink);
        return ResponseResult.SUCCESS("修改友链信息成功！");
    }
}
