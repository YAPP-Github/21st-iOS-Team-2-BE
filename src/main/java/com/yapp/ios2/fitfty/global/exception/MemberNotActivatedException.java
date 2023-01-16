package com.yapp.ios2.fitfty.global.exception;

import com.yapp.ios2.fitfty.global.response.ErrorCode;

public class MemberNotActivatedException extends BaseException {

    public MemberNotActivatedException() {
        super(ErrorCode.MEMBER_NOT_ACTIVATED);
    }

    public MemberNotActivatedException(String message) {
        super(message, ErrorCode.MEMBER_NOT_ACTIVATED);
    }
}

