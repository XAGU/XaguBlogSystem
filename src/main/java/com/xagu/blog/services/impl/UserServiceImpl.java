package com.xagu.blog.services.impl;

import com.xagu.blog.pojo.User;
import com.xagu.blog.response.ResponseResult;
import com.xagu.blog.services.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xagu
 * Created on 2020/6/22
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserServiceImpl implements IUserService {
    @Override
    public ResponseResult initManagerAccount(User user, HttpServletRequest request) {
        //检查数据
        return null;
    }
}
