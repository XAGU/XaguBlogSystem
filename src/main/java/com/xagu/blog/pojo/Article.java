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
@Table(name = " tb_article")
public class Article {

    @Id
    private String id;
    @Column(name = "title")
    private String title;
    @Column(name = "cover")
    private String cover;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "category_id")
    private String categoryId;
    @Column(name = "content")
    private String content;
    @Column(name = "type")
    private String type;
    @Column(name = "state")
    private String state;
    @Column(name = "summary")
    private String summary;
    @Column(name = "labels")
    private String labels;
    @Column(name = "view_count")
    private long viewCount;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
}
