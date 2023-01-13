package com.yapp.ios2.fitfty.interfaces.user;

import lombok.Data;

@Data
public class KakaoOAuthTokenDto {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;
}
