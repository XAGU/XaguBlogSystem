package com.xagu.blog.services.impl;

import com.xagu.blog.dao.LoopDao;
import com.xagu.blog.pojo.Looper;
import com.xagu.blog.response.ResponseResult;
import com.xagu.blog.services.ILoopService;
import com.xagu.blog.utils.SnowFlake;
import com.xagu.blog.utils.TextUtil;
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
 * Created on 2020/7/1
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class LoopServiceImpl extends BaseService implements ILoopService {
    @Autowired
    SnowFlake snowFlake;
    @Autowired
    LoopDao loopDao;

    @Override
    public ResponseResult addLoop(Looper looper) {
        //检查数据
        if (StringUtils.isEmpty(looper.getImageUrl())) {
            return ResponseResult.FAILED("轮播图链接不能为空！");
        }
        if (StringUtils.isEmpty(looper.getTargetUrl())) {
            return ResponseResult.FAILED("轮播图目标链接不能为空！");
        }
        if (StringUtils.isEmpty(looper.getTitle())) {
            return ResponseResult.FAILED("轮播图标题不能为空！");
        }
        //补全数据
        looper.setId(snowFlake.nextId() + "");
        looper.setCreateTime(new Date());
        looper.setUpdateTime(new Date());
        //保存数据
        loopDao.save(looper);
        //返回结果
        return ResponseResult.SUCCESS("新增轮播图成功！");
    }

    @Override
    public ResponseResult deleteLoop(String loopId) {
        return ResponseResult.decide(loopDao.deleteByUpdateState(loopId) > 0,
                "删除轮播图成功！",
                "删除轮播图失败！");
    }

    @Override
    public ResponseResult updateLoop(String loopId, Looper looper) {
        //查询数据
        Looper dbLoop = loopDao.findOneById(loopId);
        if (dbLoop == null) {
            return ResponseResult.FAILED("友链不存在！");
        }
        //检查数据
        String loopImageUrl = looper.getImageUrl();
        if (!StringUtils.isEmpty(loopImageUrl)) {
            dbLoop.setImageUrl(loopImageUrl);
        }
        String loopTargetUrl = looper.getTargetUrl();
        if (!StringUtils.isEmpty(loopTargetUrl)) {
            dbLoop.setTargetUrl(loopTargetUrl);
        }
        String loopTitle = looper.getTitle();
        if (!StringUtils.isEmpty(loopTitle)) {
            dbLoop.setTitle(loopTitle);
        }
        String loopState = looper.getState();
        if (!StringUtils.isEmpty(loopState)) {
            dbLoop.setState(loopState);
        }
        Long loopOrder = looper.getOrder();
        dbLoop.setOrder(loopOrder);

        dbLoop.setUpdateTime(new Date());
        //保存数据
        loopDao.save(dbLoop);
        return ResponseResult.SUCCESS("修改轮播图信息成功！");
    }

    @Override
    public ResponseResult listLoop(Integer page, Integer size) {
        page = this.checkPage(page);
        size = this.checkSize(size);
        Sort sort = Sort.by(Sort.Direction.DESC, "order", "createTime");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Looper> loopers = loopDao.findAll(pageable);
        return ResponseResult.SUCCESS("查询轮播图成功！").setData(loopers);
    }

    @Override
    public ResponseResult getLoop(String loopId) {
        Looper looper = loopDao.findOneById(loopId);
        return ResponseResult.decide(looper != null,
                "获取轮播图成功！",
                "获取轮播图失败！").setData(looper);
    }
}
