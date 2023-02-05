package com.yapp.ios2.fitfty.domain.user;

import static com.yapp.ios2.fitfty.global.util.Constants.TEMP_PASS;

import com.yapp.ios2.fitfty.domain.user.User.Gender;
import com.yapp.ios2.fitfty.domain.user.User.LoginType;
import java.util.List;
import lombok.AllArgsConstructor;
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
    @ToString
    public static class SignUp {

        private final String email;
        private final String password;
        private final LoginType type;

        public SignUp(String email, LoginType type) {
            this.email = email;
            this.password = TEMP_PASS;
            this.type = type;
        }
    }

    @Getter
    @Builder
    @ToString
    public static class CustomOption {

        private String nickname;
        private Gender gender;
        private List<String> style;
    }

    @Getter
    @Builder
    @ToString
    public static class CustomPrivacy {

        private String phoneNumber;
        private Gender gender;
        private String nickname;
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserFeed {

        private String userToken;
        private String boardToken;
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Bookmark {

        private String userToken;
        private String boardToken;
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Profile {

        private String profilePictureUrl;
        private String message;
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignInApple {
        String userIdentifier;
        String userEmail;
        String userName;
        String identityToken;
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignInKakao {

        String accessToken;
    }
}
