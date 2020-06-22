package com.xagu.blog.controller.admin;

import com.xagu.blog.pojo.Category;
import com.xagu.blog.response.ResponseResult;
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

    /**
     * 添加分类
     *
     * @return
     */
    @PostMapping
    public ResponseResult addCategory(@RequestBody Category category) {
        return null;
    }

    /**
     * 删除分类
     *
     * @return
     */
    @DeleteMapping("{categoryId}")
    public ResponseResult deleteCategory(@PathVariable("categoryId") String categoryId) {
        return null;
    }

    /**
     * 修改分类
     *
     * @return
     */
    @PostMapping("{categoryId}")
    public ResponseResult updateCategory(@PathVariable("{categoryId}") String categoryId, @RequestBody Category category) {
        return null;
    }


    /**
     * 根据Id查询分类
     *
     * @return
     */
    @GetMapping("{categoryId}")
    public ResponseResult addCategory(@PathVariable("categoryId") String categoryId, @RequestBody Category category) {
        return null;
    }

    /**
     * 查询所有分类
     *
     * @return
     */
    @GetMapping("list")
    public ResponseResult listCategory(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return null;
    }


}
