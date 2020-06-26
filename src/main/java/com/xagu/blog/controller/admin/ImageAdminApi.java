package com.xagu.blog.controller.admin;

import com.xagu.blog.response.ResponseResult;
import com.xagu.blog.services.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xagu
 * Created on 2020/6/22
 * Email:xagu_qc@foxmail.com
 * Describe: 图片的api
 */
@RestController
@RequestMapping("admin/image")
public class ImageAdminApi {


    @Autowired
    IImageService imageService;

    /**
     * 上传图片
     */
    @PostMapping
    public ResponseResult uploadImage(MultipartFile file) {
        return imageService.uploadImage(file);
    }

    /**
     * 删除图片
     */
    @DeleteMapping("{imageId}")
    public ResponseResult deleteImage(@PathVariable("imageId") String imageId) {
        return null;
    }


    /**
     * 根据id查询图片
     */
    @GetMapping("{imageId}")
    public void getImage(@PathVariable("imageId") String imageId) {
        imageService.getImage(imageId);
    }

    /**
     * 查询所有图片
     */
    @GetMapping("list")
    public ResponseResult listImage(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return null;
    }
}
