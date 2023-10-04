package com.abin.mallchat.common.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.abin.mallchat.common.user.domain.entity.User;
import com.abin.mallchat.common.user.domain.enums.WSBaseResp;
import com.abin.mallchat.common.user.domain.enums.WSRespTypeEnum;
import com.abin.mallchat.common.user.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/user/")
public class UserController {

    HashMap<Long, User> accountMap = new HashMap<Long, User>() {
        {
            put(1L, new User(1L, "user1"));
            put(2L, new User(2L, "user2"));
            put(3L, new User(3L, "user3"));
        }
    };

    @Autowired
    private WebSocketService webSocketService;

    // 测试登录，浏览器访问： 127.0.0.1/user/doLogin?username=aa&password=aa
    @PostMapping("doLogin")
    public SaResult doLogin(Long key) {
        if (accountMap.containsKey(key)) {
            StpUtil.login(key); // 这里可以用uid 但登陆模块不是重点 所以这里随便弄弄
            return SaResult.data(StpUtil.getTokenInfo());
        }
        return SaResult.error("登陆失败");
    }

    // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
    @GetMapping("isLogin")
    public String isLogin() {
        WSBaseResp<String> resp = new WSBaseResp<>();
        resp.setData("当前用户已登录");
        resp.setType(WSRespTypeEnum.LOGIN_SUCCESS.getType());
        webSocketService.sendToUid(resp, 1L);
        return "当前会话是否登录：" + StpUtil.isLogin();
    }

}

