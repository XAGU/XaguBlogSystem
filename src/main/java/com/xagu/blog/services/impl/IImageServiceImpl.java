package com.xagu.blog.services.impl;

import com.xagu.blog.response.ResponseResult;
import com.xagu.blog.services.IImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author xagu
 * Created on 2020/6/26
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Slf4j
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class IImageServiceImpl implements IImageService {

    @Autowired
    HttpServletResponse response;

    @Value("${xagu.blog.upload.image.savePath}")
    private String imagePath;

    @Override
    public ResponseResult uploadImage(MultipartFile file) {
        //判断是否有文件
        if (file.isEmpty()) {
            return ResponseResult.FAILED("上传的图片不能为空！");
        }
        //判断文件类型，我们只支持图片上传png，jpg，gif
        String contentType = file.getContentType();
        if (contentType == null) {
            return ResponseResult.FAILED("文件类型不支持！");
        }
        if (!"image/png".equals(contentType) && !"image/jpg".equals(contentType) || !"image/gif".equals(contentType)) {
            ResponseResult.FAILED("文件类型不支持！");
        }
        //获取相关数据，比如文件类型、文件名称
        String originalFilename = file.getOriginalFilename();
        log.debug("文件上传==>" + originalFilename);
        //根据我们的规则命名
        File targetFile = new File(imagePath + File.separator + originalFilename);
        //保存文件
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.FAILED("图片上传失败！");
        }
        //记录文件
        //返回结果
        return null;
    }

    @Override
    public void getImage(String imageId) {
        File file = new File(imagePath + File.separator + "bangdingwei.png");
        ServletOutputStream outputStream = null;
        FileInputStream fileInputStream = null;
        response.setContentType("image/png");
        try {
            outputStream = response.getOutputStream();
            //读取
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
