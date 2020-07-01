package com.xagu.blog.services.impl;

import com.xagu.blog.dao.SettingsDao;
import com.xagu.blog.pojo.Setting;
import com.xagu.blog.response.ResponseResult;
import com.xagu.blog.services.IWebSiteInfoService;
import com.xagu.blog.utils.Constants;
import com.xagu.blog.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xagu
 * Created on 2020/7/1
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class WebSiteInfoServiceImpl extends BaseService implements IWebSiteInfoService {

    @Autowired
    private SettingsDao settingsDao;

    @Autowired
    private SnowFlake snowFlake;

    @Override
    public ResponseResult getWebSiteTitle() {
        Setting dbTitle = settingsDao.findOneByKey(Constants.Settings.WEB_SITE_TITLE);
        return ResponseResult.decide(dbTitle != null,
                "获取网站标题成功！",
                "获取网站标题失败！").setData(dbTitle);
    }

    @Override
    public ResponseResult updateWebSiteInfo(String title) {
        if (StringUtils.isEmpty(title)) {
            return ResponseResult.FAILED("Title不能为空！");
        }
        Setting dbTitle = settingsDao.findOneByKey(Constants.Settings.WEB_SITE_TITLE);
        if (dbTitle == null) {
            dbTitle = new Setting();
            dbTitle.setId(snowFlake.nextId() + "");
            dbTitle.setKey(Constants.Settings.WEB_SITE_TITLE);
            dbTitle.setCreateTime(new Date());
        }
        dbTitle.setUpdateTime(new Date());
        dbTitle.setValue(title);
        settingsDao.save(dbTitle);
        return ResponseResult.SUCCESS("设置网站Title成功！");
    }

    @Override
    public ResponseResult getSeoInfo() {
        List<Setting> seoInfo = new ArrayList<>();
        Setting dbDesc = settingsDao.findOneByKey(Constants.Settings.WEB_SITE_DESCRIPTION);
        Setting dbKeyWord = settingsDao.findOneByKey(Constants.Settings.WEB_SITE_KEYWORDS);
        seoInfo.add(dbDesc);
        seoInfo.add(dbKeyWord);
        return ResponseResult.SUCCESS("获取SEO信息成功！").setData(seoInfo);
    }

    @Override
    public ResponseResult updateSeoInfo(String keywords, String description) {
        if (StringUtils.isEmpty(keywords)) {
            return ResponseResult.FAILED("KEYWORDS不能为空！");
        }
        if (StringUtils.isEmpty(description)) {
            return ResponseResult.FAILED("DESCRIPTION不能为空！");
        }
        Setting dbDesc = settingsDao.findOneByKey(Constants.Settings.WEB_SITE_DESCRIPTION);
        if (dbDesc == null) {
            dbDesc = new Setting();
            dbDesc.setId(snowFlake.nextId() + "");
            dbDesc.setKey(Constants.Settings.WEB_SITE_DESCRIPTION);
            dbDesc.setCreateTime(new Date());
        }
        dbDesc.setUpdateTime(new Date());
        dbDesc.setValue(description);
        settingsDao.save(dbDesc);
        Setting dbKeyWord = settingsDao.findOneByKey(Constants.Settings.WEB_SITE_KEYWORDS);
        if (dbKeyWord == null) {
            dbKeyWord = new Setting();
            dbKeyWord.setId(snowFlake.nextId() + "");
            dbKeyWord.setKey(Constants.Settings.WEB_SITE_KEYWORDS);
            dbKeyWord.setCreateTime(new Date());
        }
        dbKeyWord.setUpdateTime(new Date());
        dbKeyWord.setValue(keywords);
        settingsDao.save(dbKeyWord);
        return ResponseResult.SUCCESS("设置SEO信息成功！");
    }

    @Override
    public ResponseResult getWebSiteViewCount() {
        Setting dbViewCount = settingsDao.findOneByKey(Constants.Settings.WEB_SITE_VIEW_COUNT);
        if (dbViewCount == null) {
            dbViewCount = new Setting();
            dbViewCount.setId(snowFlake.nextId() + "");
            dbViewCount.setKey(Constants.Settings.WEB_SITE_KEYWORDS);
            dbViewCount.setCreateTime(new Date());
            dbViewCount.setUpdateTime(new Date());
            dbViewCount.setValue("1");
            settingsDao.save(dbViewCount);
        }
        return ResponseResult.SUCCESS("获取网站浏览量成功！").setData(dbViewCount);
    }
}
