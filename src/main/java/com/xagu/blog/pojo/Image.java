package com.xagu.blog.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author xagu
 */
@Data
@Entity
@Table ( name =" tb_images" )
public class Image {

  	@Id
	private String id;
  	@Column(name = "user_id" )
	private String userId;
  	@Column(name = "url" )
	private String url;
  	@Column(name = "state" )
	private String state;
  	@Column(name = "create_time" )
	private java.sql.Timestamp createTime;
  	@Column(name = "update_time" )
	private java.sql.Timestamp updateTime;
}
