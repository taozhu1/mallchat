package com.abin.mallchat.common.user.controller;

import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.abin.mallchat.common.common.domain.vo.response.ApiResult;
import com.abin.mallchat.common.common.utils.RequestHolder;
import com.abin.mallchat.common.user.domain.entity.User;
import com.abin.mallchat.common.user.service.WebSocketService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/capi/user/")
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
    @ApiOperation("登陆")
    public SaResult doLogin(Long key) {
        if (accountMap.containsKey(key)) {
            StpUtil.login(key, SaLoginConfig
                    .setDevice("PC")
                    .setExtra("name", accountMap.get(key).getName())
            );
            return SaResult.data(StpUtil.getTokenInfo());
        }
        return SaResult.error("登陆失败");
    }

    @GetMapping("/public/userInfo")
    @ApiOperation("获取用户信息")
    public ApiResult<User> getUserInfo() {
        return ApiResult.success(accountMap.get(RequestHolder.get().getUid()));
    }
}

