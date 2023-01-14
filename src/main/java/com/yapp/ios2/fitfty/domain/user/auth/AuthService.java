package com.yapp.ios2.fitfty.domain.user.auth;

import com.yapp.ios2.fitfty.domain.user.UserCommand;

public interface AuthService {

    String authorize(UserCommand.SignIn command);
}
