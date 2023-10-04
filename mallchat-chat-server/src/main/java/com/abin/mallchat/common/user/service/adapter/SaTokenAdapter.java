package com.abin.mallchat.common.user.service.adapter;

import com.abin.mallchat.common.user.domain.enums.WSBaseResp;
import com.abin.mallchat.common.user.domain.enums.WSRespTypeEnum;
import com.abin.mallchat.common.user.domain.vo.response.ws.WSLoginUrl;

public class SaTokenAdapter {
    public static WSBaseResp<WSLoginUrl> buildLoginResp(String LoginUrl) {
        WSBaseResp<WSLoginUrl> wsBaseResp = new WSBaseResp<>();
        wsBaseResp.setType(WSRespTypeEnum.LOGIN_URL.getType());
        wsBaseResp.setData(WSLoginUrl.builder().loginUrl(LoginUrl).build());
        return wsBaseResp;
    }

}
