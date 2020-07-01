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
@Table(name = " tb_images")
public class Image {

    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "name")
    private String name;
	@Column(name = "content_type")
	private String contentType;
    @Column(name = "path")
    private String path;
    @Column(name = "url")
    private String url;
    @Column(name = "state")
    private String state = "1";
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
}
