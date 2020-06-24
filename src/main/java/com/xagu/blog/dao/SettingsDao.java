package com.xagu.blog.dao;

import com.xagu.blog.pojo.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author xagu
 * Created on 2020/6/23
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public interface SettingsDao extends JpaRepository<Setting, String>, JpaSpecificationExecutor<Setting> {
    /**
     * 根据id查询
     * @param key
     * @return
     */
    Setting findOneByKey(String key);
}
