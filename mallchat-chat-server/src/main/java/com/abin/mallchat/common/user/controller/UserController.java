package com.abin.mallchat.common.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/user/")
public class UserController {

    HashMap<String, String> accountMap = new HashMap<String, String>() {
        {
            put("aa", "aa");
            put("bb", "bb");
            put("cc", "cc");
            put("dd", "dd");
        }
    };

    // 测试登录，浏览器访问： 127.0.0.1/user/doLogin?username=aa&password=aa
    @PostMapping("doLogin")
    public String doLogin(String username, String password) {
        if(accountMap.containsKey(username) && accountMap.get(username).equals(password)){
            StpUtil.login(10001);
            return "登录成功";
        }
        return "登录失败";
    }

    // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
    @GetMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }

}

