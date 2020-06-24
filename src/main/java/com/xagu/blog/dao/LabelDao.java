package com.xagu.blog.dao;

import com.xagu.blog.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author xagu
 * Created on 2020/6/23
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface LabelDao extends JpaRepository<Label, String>, JpaSpecificationExecutor<Label> {
}
