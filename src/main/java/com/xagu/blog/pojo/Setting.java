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
@Table ( name =" tb_settings" )
public class Setting {

  	@Id
	private String id;
  	@Column(name = "`key`" )
	private String key;
  	@Column(name = "`value`" )
	private String value;
  	@Column(name = "create_time" )
	private Date createTime;
  	@Column(name = "update_time" )
	private Date updateTime;
}
