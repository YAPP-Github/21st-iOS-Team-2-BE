package com.yapp.ios2.fitfty.global.exception;

import com.yapp.ios2.fitfty.global.response.ErrorCode;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED);
    }

    public UnauthorizedException(String message) {
        super(message, ErrorCode.UNAUTHORIZED);
    }
}
