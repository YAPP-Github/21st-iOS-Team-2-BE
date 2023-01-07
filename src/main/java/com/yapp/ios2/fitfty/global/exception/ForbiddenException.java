package com.yapp.ios2.fitfty.global.exception;

import com.yapp.ios2.fitfty.global.response.ErrorCode;

public class ForbiddenException extends BaseException {
    public ForbiddenException() {
        super(ErrorCode.FORBIDDEN);
    }

    public ForbiddenException(String message) {
        super(message, ErrorCode.FORBIDDEN);
    }
}
