package com.yapp.ios2.fitfty.domain.user;

import com.yapp.ios2.fitfty.domain.user.User.LoginType;
import com.yapp.ios2.fitfty.interfaces.user.UserDto.SignInDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
public class UserCommand {
    @Getter
    @Builder
    @ToString
    public static class SignIn {
        private final String email;
        private final String password;
        private final LoginType type;
    }

    public static SignIn from(SignInDto signInDto) {
        return SignIn.builder()
                .email(signInDto.getEmail())
                .password(signInDto.getPassword())
                .type(signInDto.getType())
                .build();
    }

}
