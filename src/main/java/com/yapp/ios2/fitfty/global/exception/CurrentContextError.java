package com.yapp.ios2.fitfty.global.exception;

import com.yapp.ios2.fitfty.global.response.ErrorCode;

public class CurrentContextError extends BaseException {

    public CurrentContextError() {
        super(ErrorCode.CURRENT_CONTEXT_ERROR);
    }

    public CurrentContextError(String message) {
        super(message, ErrorCode.CURRENT_CONTEXT_ERROR);
    }
}
