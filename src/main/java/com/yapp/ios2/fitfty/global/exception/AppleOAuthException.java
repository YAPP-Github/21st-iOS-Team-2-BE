package com.yapp.ios2.fitfty.global.exception;

import com.yapp.ios2.fitfty.global.response.ErrorCode;

public class AppleOAuthException extends BaseException {

    public AppleOAuthException() {
        super(ErrorCode.APPLE_OAUTH_NO_RESPONSE);
    }

    public AppleOAuthException(String message) {
        super(message, ErrorCode.APPLE_OAUTH_NO_RESPONSE);
    }
}
