package com.abin.mallchat.common.user.service.adapter;

import com.abin.mallchat.common.user.domain.entity.User;
import com.abin.mallchat.common.user.domain.enums.WSBaseResp;
import com.abin.mallchat.common.user.domain.enums.WSRespTypeEnum;
import com.abin.mallchat.common.user.domain.vo.response.ws.WSLoginSuccess;
import org.springframework.stereotype.Component;

/**
 * Description: ws消息适配器
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-03-19
 */
@Component
public class WSAdapter {

    public static WSBaseResp<WSLoginSuccess> buildLoginSuccessResp(User user, String token, boolean hasPower) {
        WSBaseResp<WSLoginSuccess> wsBaseResp = new WSBaseResp<>();
        wsBaseResp.setType(WSRespTypeEnum.LOGIN_SUCCESS.getType());
        WSLoginSuccess wsLoginSuccess = WSLoginSuccess.builder()
                .avatar(user.getAvatar())
                .name(user.getName())
                .token(token)
                .uid(user.getId())
                .power(hasPower ? 1 : 0)
                .build();
        wsBaseResp.setData(wsLoginSuccess);
        return wsBaseResp;
    }

    public static WSBaseResp<WSLoginSuccess> buildInvalidateTokenResp() {
        WSBaseResp<WSLoginSuccess> wsBaseResp = new WSBaseResp<>();
        wsBaseResp.setType(WSRespTypeEnum.INVALIDATE_TOKEN.getType());
        return wsBaseResp;
    }
}
