package com.xagu.blog.controller.admin;

import com.xagu.blog.pojo.Looper;
import com.xagu.blog.response.ResponseResult;
import com.xagu.blog.services.ILoopService;
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
@RequestMapping("admin/loop")
public class LopperAdminApi {


    @Autowired
    ILoopService loopService;

    /**
     * 添加轮播图
     */
    @PreAuthorize("@permission.admin()")
    @PostMapping
    public ResponseResult addLoop(@RequestBody Looper looper) {
        return loopService.addLoop(looper);
    }

    /**
     * 删除轮播图
     */
    @PreAuthorize("@permission.admin()")
    @DeleteMapping("{loopId}")
    public ResponseResult deleteLoop(@PathVariable("loopId") String loopId) {
        return loopService.deleteLoop(loopId);
    }

    /**
     * 修改轮播图
     */
    @PreAuthorize("@permission.admin()")
    @PutMapping("{loopId}")
    public ResponseResult updateLoop(@PathVariable("loopId") String loopId, @RequestBody Looper looper) {
        return loopService.updateLoop(loopId, looper);
    }

    /**
     * 根据id查询轮播图
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("{loopId}")
    public ResponseResult getLoop(@PathVariable("loopId") String loopId) {
        return loopService.getLoop(loopId);
    }

    /**
     * 查询所有轮播图
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("list")
    public ResponseResult listLoop(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return loopService.listLoop(page, size);
    }
}
