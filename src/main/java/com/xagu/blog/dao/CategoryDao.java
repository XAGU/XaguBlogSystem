package com.xagu.blog.dao;

import com.xagu.blog.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author xagu
 * Created on 2020/6/26
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface CategoryDao extends JpaRepository<Category, String>, JpaSpecificationExecutor<Category> {
    /**
     * 根据id查询
     *
     * @param categoryId
     * @return
     */
    Category findOneById(String categoryId);

    /**
     * 删除：修改status
     *
     * @param categoryId
     * @return
     */
    @Modifying
    @Query(nativeQuery = true, value = "update `tb_categories` set `status` = '0' where id = ?")
    Integer deleteByUpdateState(String categoryId);
}
