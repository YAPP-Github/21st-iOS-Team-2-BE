package com.yapp.ios2.fitfty.global.exception;

import com.yapp.ios2.fitfty.global.response.ErrorCode;

public class PictureNotFoundException extends BaseException {

    public PictureNotFoundException() {
        super(ErrorCode.PICTURE_NOT_FOUND);
    }

    public PictureNotFoundException(String message) {
        super(message, ErrorCode.PICTURE_NOT_FOUND);
    }
}
