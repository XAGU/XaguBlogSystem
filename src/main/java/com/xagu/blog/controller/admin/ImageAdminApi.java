package com.xagu.blog.controller.admin;

import com.xagu.blog.response.ResponseResult;
import com.xagu.blog.services.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("@permission.admin()")
    @PostMapping
    public ResponseResult uploadImage(MultipartFile file) {
        return imageService.uploadImage(file);
    }

    /**
     * 删除图片
     */
    @PreAuthorize("@permission.admin()")
    @DeleteMapping("{imageId}")
    public ResponseResult deleteImage(@PathVariable("imageId") String imageId) {
        return imageService.deleteImage(imageId);
    }


    /**
     * 根据id查询图片
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("{imageId}")
    public ResponseResult getImage(@PathVariable("imageId") String imageId) {
        return imageService.getImage(imageId);
    }

    /**
     * 查询所有图片
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("list/{page}/{size}")
    public ResponseResult listImage(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return imageService.listImage(page, size);
    }
}
