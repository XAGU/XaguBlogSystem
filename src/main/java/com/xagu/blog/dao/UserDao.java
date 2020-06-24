package com.xagu.blog.dao;

import com.xagu.blog.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author xagu
 * Created on 2020/6/23
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    /**
     * 根据用户名查找
     * @param username
     * @return
     */
    User findOneByUserName(String username);

    /**
     * 根据邮箱查询
     * @param email
     * @return
     */
    User findOneByEmail(String email);
}
