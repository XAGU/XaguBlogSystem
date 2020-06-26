package com.xagu.blog.services.impl;

import com.xagu.blog.dao.CategoryDao;
import com.xagu.blog.pojo.Category;
import com.xagu.blog.response.ResponseResult;
import com.xagu.blog.services.ICategoryService;
import com.xagu.blog.utils.Constants;
import com.xagu.blog.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author xagu
 * Created on 2020/6/26
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Slf4j
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public ResponseResult addCategory(Category category) {
        //检查数据
        //必须的数据：分类名称、分类的拼音、顺序、描述
        if (StringUtils.isEmpty(category.getName())) {
            return ResponseResult.FAILED("分类名称不能为空！");
        }
        if (StringUtils.isEmpty(category.getPinyin())) {
            return ResponseResult.FAILED("分类拼音不能为空！");
        }
        if (StringUtils.isEmpty(category.getDescription())) {
            return ResponseResult.FAILED("分类描述不能为空！");
        }
        //补全数据
        category.setId(String.valueOf(snowFlake.nextId()));
        category.setStatus("1");
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        //保存数据
        categoryDao.save(category);
        return ResponseResult.SUCCESS("添加分类成功！");
    }

    @Override
    public ResponseResult getCategory(String categoryId) {
        Category category = categoryDao.findOneById(categoryId);
        return ResponseResult.decide(category != null,
                "获取分类成功！",
                "分类不存在！").setData(category);
    }

    @Override
    public ResponseResult listCategory(Integer page, Integer size) {
        //分页查询
        if (page < Constants.Page.DEFAULT_PAGE) {
            page = Constants.Page.DEFAULT_PAGE;
        }
        //最少查5个
        if (size < Constants.Page.MIN_SIZE) {
            size = Constants.Page.MIN_SIZE;
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime", "order");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return ResponseResult.SUCCESS("获取分类列表成功！")
                .setData(categoryDao.findAll(pageable));
    }

    @Override
    public ResponseResult updateCategory(String categoryId, Category category) {
        //查出来
        Category dbCategory = categoryDao.findOneById(categoryId);
        if (dbCategory == null) {
            return ResponseResult.FAILED("分类不存在！");
        }
        //判断内容
        String categoryName = category.getName();
        if (!StringUtils.isEmpty(categoryName)) {
            dbCategory.setName(categoryName);
        }
        String categoryDescription = category.getDescription();
        if (!StringUtils.isEmpty(categoryDescription)) {
            dbCategory.setDescription(categoryDescription);
        }
        String categoryPinyin = category.getPinyin();
        if (!StringUtils.isEmpty(categoryPinyin)) {
            dbCategory.setPinyin(categoryPinyin);
        }
        Long categoryOrder = category.getOrder();
        if (categoryOrder != null) {
            dbCategory.setOrder(categoryOrder);
        }
        dbCategory.setUpdateTime(new Date());
        //保存数据
        categoryDao.save(dbCategory);
        //返回结果
        return ResponseResult.SUCCESS("修改分类成功！");
    }

    @Override
    public ResponseResult deleteCategory(String categoryId) {
        Integer result = categoryDao.deleteByUpdateState(categoryId);
        return ResponseResult.decide(result > 0,
                "删除分类成功！",
                "删除分类失败");
    }
}
