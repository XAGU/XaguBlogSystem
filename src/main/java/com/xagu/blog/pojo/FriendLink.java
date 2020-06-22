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
@Table ( name =" tb_friends" )
public class FriendLink {

  	@Id
	private String id;
  	@Column(name = "name" )
	private String name;
  	@Column(name = "logo" )
	private String logo;
  	@Column(name = "url" )
	private String url;
  	@Column(name = "`order`" )
	private long order;
  	@Column(name = "state" )
	private String state;
  	@Column(name = "create_time" )
	private java.sql.Timestamp createTime;
  	@Column(name = "update_time" )
	private java.sql.Timestamp updateTime;
}
