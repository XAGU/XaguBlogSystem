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
@Table ( name =" tb_categories" )
public class Category {

  	@Id
	private String id;
  	@Column(name = "name" )
	private String name;
  	@Column(name = "pinyin" )
	private String pinyin;
  	@Column(name = "description" )
	private String description;
  	@Column(name = "`order`" )
	private long order;
  	@Column(name = "status" )
	private String status;
  	@Column(name = "create_time" )
	private java.sql.Timestamp createTime;
  	@Column(name = "update_time" )
	private java.sql.Timestamp updateTime;
}
