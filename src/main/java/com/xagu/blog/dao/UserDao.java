package com.xagu.blog.dao;

import com.xagu.blog.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author xagu
 * Created on 2020/6/23
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    /**
     * 根据用户名查找
     *
     * @param username
     * @return
     */
    User findOneByUserName(String username);

    /**
     * 根据邮箱查询
     *
     * @param email
     * @return
     */
    User findOneByEmail(String email);

    /**
     * 根据id查
     *
     * @param id
     * @return
     */
    User findOneById(String id);

    /**
     * 分页查询，除去密码
     *
     * @param pageable
     * @return
     */
    @Query(value = "select new User(u.id,u.userName,u.roles,u.avatar,u.email,u.sign,u.state,u.loginIp,u.regIp,u.createTime,u.updateTime) from User as u")
    Page<User> listAllUserNoPassword(Pageable pageable);

    /**
     * 修改密码通过邮箱
     *
     * @param encode
     * @param email
     * @return
     */
    @Modifying
    @Query(nativeQuery = true, value = "update `tb_user` set `password` = ? where `email` = ?")
    Integer updatePasswordByEmail(String encode, String email);

    /**
     * 修改邮箱通过id
     *
     * @param email
     * @param id
     * @return
     */
    @Modifying
    @Query(nativeQuery = true, value = "update `tb_user` set `email` = ? where `id` = ?")
    Integer updateEmailById(String email, String id);
}
