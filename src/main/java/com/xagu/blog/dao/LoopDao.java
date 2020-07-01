package com.xagu.blog.dao;

import com.xagu.blog.pojo.Image;
import com.xagu.blog.pojo.Looper;
import com.xagu.blog.response.ResponseResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author xagu
 * Created on 2020/7/1
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface LoopDao extends JpaRepository<Looper, String>, JpaSpecificationExecutor<Looper> {

    /**
     * 删除轮播图，通过更新状态
     *
     * @param loopId
     * @return
     */
    @Modifying
    @Query(nativeQuery = true, value = "update `tb_looper` set `state` = '0' where id = ?")
    int deleteByUpdateState(String loopId);

    /**
     * 根据id查询轮播图
     * @param loopId
     * @return
     */
    Looper findOneById(String loopId);
}
