package com.xagu.blog.services;

import com.xagu.blog.pojo.Category;
import com.xagu.blog.response.ResponseResult;

/**
 * @author xagu
 * Created on 2020/6/26
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface ICategoryService {
    /**
     * 添加分类
     * @param category
     * @return
     */
    ResponseResult addCategory(Category category);

    /**
     * 查询分类id
     * @param categoryId
     * @return
     */
    ResponseResult getCategory(String categoryId);

    /**
     * 分页查询所有
     * @param page
     * @param size
     * @return
     */
    ResponseResult listCategory(Integer page, Integer size);

    /**
     * 修改分类
     * @param categoryId
     * @param category
     * @return
     */
    ResponseResult updateCategory(String categoryId, Category category);

    /**
     * 删除分类
     * @param categoryId
     * @return
     */
    ResponseResult deleteCategory(String categoryId);
}
