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
@Table ( name =" tb_user" )
public class User {

  	@Id
	private String id;
  	@Column(name = "user_name" )
	private String userName;
  	@Column(name = "password" )
	private String password;
  	@Column(name = "roles" )
	private String roles;
  	@Column(name = "avatar" )
	private String avatar;
  	@Column(name = "email" )
	private String email;
  	@Column(name = "sign" )
	private String sign;
  	@Column(name = "state" )
	private String state;
  	@Column(name = "reg_ip" )
	private String regIp;
  	@Column(name = "login_ip" )
	private String loginIp;
  	@Column(name = "create_time" )
	private java.sql.Timestamp createTime;
  	@Column(name = "update_time" )
	private java.sql.Timestamp updateTime;
}
