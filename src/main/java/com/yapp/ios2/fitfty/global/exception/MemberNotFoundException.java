package com.yapp.ios2.fitfty.global.exception;

import com.yapp.ios2.fitfty.global.response.ErrorCode;

public class MemberNotFoundException extends BaseException {

    public MemberNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }

    public MemberNotFoundException(String message) {
        super(message, ErrorCode.MEMBER_NOT_FOUND);
    }
}
