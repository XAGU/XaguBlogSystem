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
@Table ( name =" tb_looper" )
public class Looper {

  	@Id
	private String id;
  	@Column(name = "title" )
	private String title;
  	@Column(name = "`order`" )
	private long order;
  	@Column(name = "state" )
	private String state;
  	@Column(name = "target_url" )
	private String targetUrl;
  	@Column(name = "image_url" )
	private String imageUrl;
  	@Column(name = "create_time" )
	private java.sql.Timestamp createTime;
  	@Column(name = "update_time" )
	private java.sql.Timestamp updateTime;
}
