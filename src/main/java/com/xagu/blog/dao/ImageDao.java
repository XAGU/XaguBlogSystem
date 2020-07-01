package com.xagu.blog.dao;

import com.xagu.blog.pojo.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author xagu
 * Created on 2020/6/30
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface ImageDao extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {
    /**
     * 删除图片通过修改状态
     *
     * @param imageId
     * @return
     */
    @Modifying
    @Query(nativeQuery = true, value = "update `tb_images` set `state` = '0' where `id` = ?")
    int deleteImageByUpdateState(String imageId);
}
