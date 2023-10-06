package com.abin.mallchat.common.common.interceptor;

import com.abin.mallchat.common.common.exception.HttpErrorEnum;
import com.abin.mallchat.common.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    public static final String HEAD_AUTHORIZATION = "Authorization";
    public static final String AUTHORIZATION_SCHEMA = "Bearer ";
    public static final String UID = "uid";

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = getToken(request);
        String validUid = loginService.getValidUid(token);
        if (Objects.nonNull(validUid)) { // 登陆态
            request.setAttribute(UID, validUid);
        } else {
            // 判断接口权限
            boolean isPublicURI = isPublicURI(request);
            if (!isPublicURI) {// 401
                HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
                return false;
            }
        }
        return true;
    }

    private static boolean isPublicURI(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String[] split = requestURI.split("/");
        return split.length > 3 && "public".equals(split[3]);
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader(HEAD_AUTHORIZATION);
        Optional.ofNullable(header)
                .filter(h -> h.startsWith(AUTHORIZATION_SCHEMA))
                .map(h -> h.replaceFirst(AUTHORIZATION_SCHEMA, ""))
                .orElse(null);
        return header;
    }
}
