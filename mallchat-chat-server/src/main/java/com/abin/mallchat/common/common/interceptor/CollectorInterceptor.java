package com.abin.mallchat.common.common.interceptor;

import cn.hutool.extra.servlet.ServletUtil;
import com.abin.mallchat.common.common.domain.dto.RequestInfo;
import com.abin.mallchat.common.common.utils.RequestHolder;
import com.abin.mallchat.common.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class CollectorInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long uid = Optional.ofNullable(request.getAttribute(TokenInterceptor.UID))
                .map(Object::toString)
                .map(Long::parseLong)
                .orElse(null);
        String clientIP = ServletUtil.getClientIP(request);
        RequestInfo requestInfo = new RequestInfo(uid, clientIP);
        RequestHolder.set(requestInfo);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestHolder.remove();
    }
}
