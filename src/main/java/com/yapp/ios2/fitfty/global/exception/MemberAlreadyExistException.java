package com.yapp.ios2.fitfty.global.exception;

import com.yapp.ios2.fitfty.global.response.ErrorCode;

public class MemberAlreadyExistException extends BaseException {

    public MemberAlreadyExistException() {
        super(ErrorCode.MEMBER_ALREADY_EXIST);
    }

    public MemberAlreadyExistException(String message) {
        super(message, ErrorCode.MEMBER_ALREADY_EXIST);
    }

}
