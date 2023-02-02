package com.yapp.ios2.fitfty.domain.user.auth;

import com.yapp.ios2.fitfty.domain.user.UserCommand;

public interface AuthService {

    String login(UserCommand.SignIn command);

    String loginWithKakaoCode(String code);

    void unActivateUser();

    String loginWithKakao(String accessToken);
    String loginWithApple(String token);
}
