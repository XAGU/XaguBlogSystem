package com.xagu.blog.services.impl;

import com.xagu.blog.utils.Constants;

/**
 * @author xagu
 * Created on 2020/6/30
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public class BaseService {
    protected int checkPage(int page) {
        if (page < Constants.Page.DEFAULT_PAGE) {
            page = Constants.Page.DEFAULT_PAGE;
        }
        return page;
    }

    protected int checkSize(int size) {
        //最少查5个
        if (size < Constants.Page.MIN_SIZE) {
            size = Constants.Page.MIN_SIZE;
        }
        return size;
    }
}
