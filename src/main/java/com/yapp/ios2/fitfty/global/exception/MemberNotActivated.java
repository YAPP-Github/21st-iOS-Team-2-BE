package com.yapp.ios2.fitfty.global.exception;

import com.yapp.ios2.fitfty.global.response.ErrorCode;

public class MemberNotActivated extends BaseException {

    public MemberNotActivated() {
        super(ErrorCode.MEMBER_NOT_ACTIVATED);
    }

    public MemberNotActivated(String message) {
        super(message, ErrorCode.MEMBER_NOT_ACTIVATED);
    }
}

