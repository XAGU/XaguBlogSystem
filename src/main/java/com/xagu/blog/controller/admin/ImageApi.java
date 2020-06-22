package com.xagu.blog.controller.admin;

import com.xagu.blog.pojo.Images;
import com.xagu.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author xagu
 * Created on 2020/6/22
 * Email:xagu_qc@foxmail.com
 * Describe: 图片的api
 */
@RestController
@RequestMapping("admin/image")
public class ImageApi {

    /**
     * 上传图片
     */
    @PostMapping
    public ResponseResult uploadImage() {
        return null;
    }

    /**
     * 删除图片
     */
    @DeleteMapping("{imageId}")
    public ResponseResult deleteImage(@PathVariable("imageId") String imageId) {
        return null;
    }

    /**
     * 修改图片
     */
    @PutMapping("{imageId}")
    public ResponseResult updateImage(@PathVariable("imageId") String imageId, @RequestBody Images images) {
        return null;
    }

    /**
     * 根据id查询图片
     */
    @GetMapping("{imageId}")
    public ResponseResult getImage(@PathVariable("imageId") String imageId) {
        return null;
    }

    /**
     * 查询所有图片
     */
    @GetMapping("list")
    public ResponseResult listImage(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return null;
    }
}
