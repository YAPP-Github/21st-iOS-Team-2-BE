package com.yapp.ios2.fitfty.domain.user.auth;

import com.yapp.ios2.fitfty.domain.user.UserCommand;
import com.yapp.ios2.fitfty.domain.user.UserInfo;

public interface AuthService {

    String login(UserCommand.SignIn command);

    String loginWithKakaoCode(String code);

    void unActivateUser();

    UserInfo.SignInInfo loginWithKakao(UserCommand.SignInKakao command);

    UserInfo.SignInInfo loginWithApple(UserCommand.SignInApple command);

    String getRole();
}
