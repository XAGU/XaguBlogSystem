package com.xagu.blog.controller.admin;

import com.xagu.blog.pojo.Looper;
import com.xagu.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author xagu
 * Created on 2020/6/22
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@RestController
@RequestMapping("admin/loop")
public class LopperApi {

    /**
     * 添加轮播图
     */
    @PostMapping
    public ResponseResult addLoop(@RequestBody Looper looper) {
        return null;
    }

    /**
     * 删除轮播图
     */
    @DeleteMapping("{loopId}")
    public ResponseResult deleteLoop(@PathVariable("loopId") String loopId) {
        return null;
    }

    /**
     * 修改轮播图
     */
    @PutMapping("{loopId}")
    public ResponseResult updateLoop(@PathVariable("loopId") String loopId, @RequestBody Looper looper) {
        return null;
    }

    /**
     * 根据id查询轮播图
     */
    @GetMapping("{loopId}")
    public ResponseResult getLoop(@PathVariable("loopId") String loopId) {
        return null;
    }

    /**
     * 查询所有轮播图
     */
    @GetMapping("list")
    public ResponseResult listLoop(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return null;
    }
}
