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
@Entity
@Data
@Table(name = " tb_refresh_token")
public class RefreshToken {

    @Id
    private String id;
    @Column(name = "refresh_token")
    private String refreshToken;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "token_key")
    private String tokenKey;
	@Column(name = "create_time" )
	private Date createTime;
	@Column(name = "update_time" )
	private Date updateTime;
}
