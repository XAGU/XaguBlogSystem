package com.xagu.blog.services.impl;

import com.xagu.blog.dao.ImageDao;
import com.xagu.blog.pojo.Image;
import com.xagu.blog.pojo.User;
import com.xagu.blog.response.ResponseResult;
import com.xagu.blog.services.IImageService;
import com.xagu.blog.services.IUserService;
import com.xagu.blog.utils.Constants;
import com.xagu.blog.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xagu
 * Created on 2020/6/26
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Slf4j
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class ImageServiceImpl extends BaseService implements IImageService {

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private IUserService userService;

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private SnowFlake snowFlake;

    private static SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat("yyyy_MM_dd");

    @Value("${xagu.blog.upload.image.savePath}")
    private String imagePath;

    @Value("${xagu.blog.upload.image.maxSize}")
    private long maxSize;

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
        //获取相关数据，比如文件类型、文件名称
        String originalFilename = file.getOriginalFilename();
        log.debug("文件上传==>" + originalFilename);
        String type = getType(contentType, originalFilename);
        if (type == null) {
            return ResponseResult.FAILED("文件类型不支持！");
        }
        //限制文件大小
        long size = file.getSize();
        if (size > maxSize) {
            return ResponseResult.FAILED("图片最大仅支持" + (maxSize / 1024) + "kb");
        }
        //创建图片的保存目录
        long currentTime = System.currentTimeMillis();
        long imageId = snowFlake.nextId();
        //规则：配置目录/日期/类型/ID.类型
        String targetPath = imagePath + File.separator +
                sSimpleDateFormat.format(currentTime) + File.separator +
                type + File.separator +
                imageId + "." + type;
        File targetFile = new File(targetPath);
        if (!targetFile.getParentFile().exists()) {
            targetFile.mkdirs();
        }
        log.debug("targetFile ==>" + targetFile);
        try {
            //保存文件
            file.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.FAILED("图片上传失败！");
        }
        String url = currentTime + "_" + imageId + "." + type;
        //记录文件
        Image image = new Image();
        image.setId(imageId + "");
        image.setUrl(url);
        image.setContentType(contentType);
        image.setPath(targetFile.getPath());
        image.setName(originalFilename);
        image.setCreateTime(new Date(currentTime));
        image.setUpdateTime(new Date(currentTime));
        User user = userService.checkUser();
        image.setUserId(user.getId());
        imageDao.save(image);
        Map<String, String> result = new HashMap<>();
        result.put("id", url);
        result.put("name", originalFilename);
        //返回结果 访问路径 名称 alt
        return ResponseResult.SUCCESS("上传图片成功！").setData(result);
    }

    private String getType(String contentType, String originalFilename) {
        String type = null;
        if (Constants.ImageType.TYPE_PNG_WITH_PREFIX.equals(contentType) &&
                originalFilename.endsWith(Constants.ImageType.TYPE_PNG)) {
            type = Constants.ImageType.TYPE_PNG;
        } else if (Constants.ImageType.TYPE_JPG_WITH_PREFIX.equals(contentType) &&
                originalFilename.endsWith(Constants.ImageType.TYPE_JPG)) {
            type = Constants.ImageType.TYPE_JPG;
        } else if (Constants.ImageType.TYPE_GIF_WITH_PREFIX.equals(contentType) &&
                originalFilename.endsWith(Constants.ImageType.TYPE_GIF)) {
            type = Constants.ImageType.TYPE_GIF;
        } else if (Constants.ImageType.TYPE_JPEG_WITH_PREFIX.equals(contentType) &&
                originalFilename.endsWith(Constants.ImageType.TYPE_JPG)) {
            type = Constants.ImageType.TYPE_JPG;
        }
        return type;
    }

    @Override
    public ResponseResult getImage(String imageId) {
        String[] split = imageId.split("_");
        if (split.length != 2) {
            return ResponseResult.FAILED("图片不存在！");
        }
        long picTime = Long.parseLong(split[0]);
        String[] idAndType = split[1].split("\\.");
        File file = new File(imagePath + File.separator +
                sSimpleDateFormat.format(picTime) + File.separator +
                idAndType[1] + File.separator +
                split[1]);
        if (!file.exists()) {
            return ResponseResult.FAILED("文件不存在！");
        }
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
        return null;
    }

    @Override
    public ResponseResult listImage(Integer page, Integer size) {
        page = this.checkPage(page);
        size = this.checkSize(size);
        User currentUser = userService.checkUser();
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Image> images = imageDao.findAll(new Specification<Image>() {
            @Override
            public Predicate toPredicate(Root<Image> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //根据用户id
                Predicate userPredicate = criteriaBuilder.equal(root.get("userId").as(String.class), currentUser.getId());
                //根据状态
                Predicate statePredicate = criteriaBuilder.equal(root.get("state").as(String.class), "1");
                return criteriaBuilder.and(userPredicate, statePredicate);
            }
        }, pageable);
        return ResponseResult.SUCCESS("获取列表成功！").setData(images);
    }

    @Override
    public ResponseResult deleteImage(String imageId) {
        return ResponseResult.decide(imageDao.deleteImageByUpdateState(imageId) > 0,
                "删除图片成功！",
                "删除图片失败！");
    }
}
