package com.xagu.blog.services.impl;

import com.xagu.blog.dao.ArticleDao;
import com.xagu.blog.pojo.Article;
import com.xagu.blog.pojo.User;
import com.xagu.blog.response.ResponseResult;
import com.xagu.blog.response.ResponseState;
import com.xagu.blog.services.IArticleService;
import com.xagu.blog.services.IUserService;
import com.xagu.blog.utils.Constants;
import com.xagu.blog.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xagu
 * Created on 2020/7/1
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class ArticleServiceImpl extends BaseService implements IArticleService {

    @Autowired
    private IUserService userService;
    @Autowired
    private SnowFlake snowFlake;
    @Autowired
    private ArticleDao articleDao;

    @Override
    public ResponseResult postArticle(Article article) {
        //检查用户，获取用户对象
        User currentUser = userService.checkUser();
        if (currentUser == null) {
            return ResponseResult.state(ResponseState.ACCOUNT_NO_LOGIN);
        }
        //检查数据
        String title = article.getTitle();
        if (StringUtils.isEmpty(title)) {
            return ResponseResult.FAILED("文章标题不能为空！");
        }
        if (title.length() > Constants.Article.TITLE_MAX_LENGTH) {
            return ResponseResult.FAILED("文章标题不能超过" + Constants.Article.TITLE_MAX_LENGTH + "个字符！");
        }
        //2钟，草稿， 发布
        String state = article.getState();
        if (!Constants.Article.STATE_PUBLISH.equals(state) && !Constants.Article.STATE_DRAFT.equals(state)) {
            ResponseResult.FAILED("文章状态异常！");
        }
        String type = article.getType();
        if (StringUtils.isEmpty(type)) {
            return ResponseResult.FAILED("文章类型不能为空！");
        }
        if (!"0".equals(type) && !"1".equals(type)) {
            return ResponseResult.FAILED("类型格式不正确！");
        }
        //以下是发布检查，草稿不需要检查
        if (Constants.Article.STATE_PUBLISH.equals(state)) {
            if (StringUtils.isEmpty(article.getContent())) {
                return ResponseResult.FAILED("文章内容不能为空！");
            }
            if (StringUtils.isEmpty(article.getCategoryId())) {
                return ResponseResult.FAILED("文章分类id不能为空！");
            }
            String summary = article.getSummary();
            if (StringUtils.isEmpty(summary)) {
                return ResponseResult.FAILED("文章摘要不能为空！");
            }
            if (summary.length() > Constants.Article.SUMMARY_MAX_LENGTH) {
                return ResponseResult.FAILED("文章摘要不能超过" + Constants.Article.SUMMARY_MAX_LENGTH + "个字符！");
            }
            if (StringUtils.isEmpty(article.getLabels())) {
                return ResponseResult.FAILED("文章标签不能为空！");
            }
        }
        String articleId = article.getId();
        if (StringUtils.isEmpty(articleId)) {
            //新的内容，数据库里面没有
            //补充数据
            article.setId(snowFlake.nextId() + "");
            article.setCreateTime(new Date());
            article.setViewCount(0L);
        } else {
            //更新内容，对状态进行处理，如果是已经发布的，不能设置为草稿
            Article dbArticle = articleDao.findOneById(articleId);
            if (Constants.Article.STATE_PUBLISH.equals(dbArticle.getState()) &&
                    Constants.Article.STATE_DRAFT.equals(state)) {
                //已经发布的，不能设置为草稿
                return ResponseResult.FAILED("已发布的文章不能保存为草稿！");
            }
        }
        article.setUserId(currentUser.getId());
        article.setUpdateTime(new Date());
        //保存数据
        articleDao.save(article);
        //TODO:保存到搜索的数据库
        //返回结果
        return ResponseResult.SUCCESS(Constants.Article.STATE_DRAFT.equals(state) ? "文章草稿保存成功！" : "文章保存成功！").setData(article.getId());
    }

    @Override
    public ResponseResult listArticle(Integer page, Integer size, String state, String keyword, String categoryId) {
        //处理一下size和page
        page = checkPage(page);
        size = checkPage(size);
        //创建分页和排序条件
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        //开始查询
        Page<Article> articles = articleDao.findAll(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(state)) {
                    predicates.add(criteriaBuilder.equal(root.get("state").as(String.class), state));
                }
                if (!StringUtils.isEmpty(categoryId)) {
                    predicates.add(criteriaBuilder.equal(root.get("categoryId"), categoryId));
                }
                if (!StringUtils.isEmpty(keyword)) {
                    predicates.add(criteriaBuilder.like(root.get("content"), "%" + keyword + "%"));
                }
                Predicate[] predicate = new Predicate[predicates.size()];
                predicates.toArray(predicate);
                return criteriaBuilder.and(predicate);
            }
        }, pageable);
        //处理查询条件
        //返回结果
        return ResponseResult.SUCCESS("获取文章成功！").setData(articles);
    }
}
