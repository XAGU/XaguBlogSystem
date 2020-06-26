package com.xagu.blog.controller.admin;

import com.xagu.blog.pojo.Category;
import com.xagu.blog.response.ResponseResult;
import com.xagu.blog.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author xagu
 * Created on 2020/6/22
 * Email:xagu_qc@foxmail.com
 * Describe: 分类的api
 */
@RestController
@RequestMapping("admin/category")
public class CategoryAdminApi {

    @Autowired
    ICategoryService categoryService;

    /**
     * 添加分类
     * 需要管理员权限
     *
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @PostMapping
    public ResponseResult addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    /**
     * 删除分类
     *
     * @return
     */
    @DeleteMapping("{categoryId}")
    public ResponseResult deleteCategory(@PathVariable("categoryId") String categoryId) {
        return categoryService.deleteCategory(categoryId);
    }

    /**
     * 修改分类
     *
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @PutMapping("{categoryId}")
    public ResponseResult updateCategory(@PathVariable("categoryId") String categoryId, @RequestBody Category category) {
        return categoryService.updateCategory(categoryId, category);
    }


    /**
     * 根据Id查询分类
     * 使用的case
     * 修改的时候，获取一下，填充弹窗
     * <p>
     * 管理员权限
     *
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("{categoryId}")
    public ResponseResult getCategory(@PathVariable("categoryId") String categoryId) {
        return categoryService.getCategory(categoryId);
    }

    /**
     * 查询所有分类
     * 管理员权限
     *
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("list/{page}/{size}")
    public ResponseResult listCategory(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return categoryService.listCategory(page, size);
    }


}
