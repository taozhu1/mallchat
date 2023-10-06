package com.abin.mallchat.common.user.service;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{
    @Override
    public boolean verify(String token) {
        return false;
    }

    @Override
    public void renewalTokenIfNecessary(String token) {

    }

    @Override
    public String login(Long uid) {
        return null;
    }

    @Override
    public String getValidUid(String token) {
        return (String) StpUtil.getLoginIdByToken(token);
    }
}
