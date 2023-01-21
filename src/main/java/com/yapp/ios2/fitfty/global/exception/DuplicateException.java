package com.yapp.ios2.fitfty.global.exception;

import com.yapp.ios2.fitfty.global.response.ErrorCode;

public class DuplicateException extends BaseException{

    public DuplicateException() {
        super(ErrorCode.DUPLICATE_ENTITY);
    }

    public DuplicateException(String message) {
        super(message, ErrorCode.DUPLICATE_ENTITY);
    }

}
