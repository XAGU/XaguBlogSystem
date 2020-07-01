package com.xagu.blog.dao;

import com.xagu.blog.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author xagu
 * Created on 2020/7/1
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface ArticleDao extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {

    /**
     * 根据id查询文章
     * @param articleId
     * @return
     */
    Article findOneById(String articleId);
}
