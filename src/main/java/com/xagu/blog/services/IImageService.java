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
    void getImage(String imageId);
}
