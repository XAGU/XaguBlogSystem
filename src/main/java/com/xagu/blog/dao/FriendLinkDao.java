package com.xagu.blog.dao;

import com.xagu.blog.pojo.FriendLink;
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
public interface FriendLinkDao extends JpaRepository<FriendLink, String>, JpaSpecificationExecutor<FriendLink> {
    /**
     * 根据id查询
     *
     * @param friendLinkId
     * @return
     */
    FriendLink findOneById(String friendLinkId);

    /**
     * 删除：修改status
     *
     * @param friendLinkId
     * @return
     */
    @Modifying
    @Query(nativeQuery = true, value = "update `tb_friends` set `state` = '0' where id = ?")
    Integer deleteByUpdateState(String friendLinkId);
}
