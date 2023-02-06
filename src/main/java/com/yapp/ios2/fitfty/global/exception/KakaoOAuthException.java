package com.yapp.ios2.fitfty.global.exception;

import com.yapp.ios2.fitfty.global.response.ErrorCode;

public class KakaoOAuthException extends BaseException {

    public KakaoOAuthException() {
        super(ErrorCode.KAKAO_OAUTH_NO_RESPONSE);
    }

    public KakaoOAuthException(String message) {
        super(message, ErrorCode.KAKAO_OAUTH_NO_RESPONSE);
    }
}
