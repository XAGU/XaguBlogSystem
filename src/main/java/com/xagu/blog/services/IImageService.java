package com.xagu.blog.services;

import com.xagu.blog.response.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xagu
 * Created on 2020/6/26
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface IImageService {
    /**
     * 上传图片
     * @param file
     * @return
     */
    ResponseResult uploadImage(MultipartFile file);

    /**
     * 获取图片
     * @param imageId
     * @return
     */
    ResponseResult getImage(String imageId);

    /**
     * 分页查询图片
     * @param page
     * @param size
     * @return
     */
    ResponseResult listImage(Integer page, Integer size);

    /**
     * 根据图片id删除图片
     * @param imageId
     * @return
     */
    ResponseResult deleteImage(String imageId);
}
