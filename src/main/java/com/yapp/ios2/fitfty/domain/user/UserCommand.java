package com.yapp.ios2.fitfty.domain.user;

import com.yapp.ios2.fitfty.domain.user.User.LoginType;
import com.yapp.ios2.fitfty.interfaces.user.UserDto.SignInDto;
import com.yapp.ios2.fitfty.interfaces.user.UserDto.SignUpDto;
import java.util.List;
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
    }

    @Getter
    @Builder
    @ToString
    public static class SignUp {
        private final String email;
        private final String password;
        private final LoginType type;
    }

    @Getter
    @Builder
    @ToString
    public static class CustomOption {
        private String nickname;
        private String gender;
        private List<String> style;
    }

    public static SignIn toSignIn(SignInDto signInDto) {
        return SignIn.builder()
                .email(signInDto.getEmail())
                .password(signInDto.getPassword())
                .build();
    }

    public static SignUp toSignUp(SignUpDto signUpDto) {
        return SignUp.builder()
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .type(signUpDto.getType())
                .build();
    }
}
