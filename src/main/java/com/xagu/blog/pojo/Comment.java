package com.xagu.blog.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author xagu
 */
@Data
@Entity
@Table(name = " tb_comment")
public class Comment {

    @Id
    private String id;
    @Column(name = "parent_content")
    private String parentContent;
    @Column(name = "article_id")
    private String articleId;
    @Column(name = "content")
    private String content;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_avatar")
    private String userAvatar;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "state")
    private String state = "1";
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
}
