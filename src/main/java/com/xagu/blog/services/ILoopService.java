package com.xagu.blog.services;

import com.xagu.blog.pojo.Looper;
import com.xagu.blog.response.ResponseResult;

/**
 * @author xagu
 * Created on 2020/7/1
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface ILoopService {
    /**
     * 添加轮播图
     * @param looper
     * @return
     */
    ResponseResult addLoop(Looper looper);

    /**
     * 根据id删除轮播图
     * @param loopId
     * @return
     */
    ResponseResult deleteLoop(String loopId);

    /**
     * 修改loop
     * @param loopId
     * @param looper
     * @return
     */
    ResponseResult updateLoop(String loopId, Looper looper);

    /**
     * 分页查询轮播图
     * @param page
     * @param size
     * @return
     */
    ResponseResult listLoop(Integer page, Integer size);

    /**
     * 查询轮播图根据id
     * @param loopId
     * @return
     */
    ResponseResult getLoop(String loopId);
}
