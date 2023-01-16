package com.yapp.ios2.fitfty.interfaces.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yapp.ios2.fitfty.domain.user.User;
import com.yapp.ios2.fitfty.domain.user.User.LoginType;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class UserDto {
    @Data
    public static class KakaoOAuthTokenDto {
        private String access_token;
        private String token_type;
        private String refresh_token;
        private int expires_in;
        private String scope;
        private int refresh_token_expires_in;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoProfileDto {
        public Long id;

        @JsonProperty(value = "connected_at")
        public String connectedAt;
        public Properties properties;

        @JsonProperty(value = "kakao_account")
        public KakaoAccount kakaoAccount;

        @Data
        public class Properties {
            public String nickname;
        }

        @Data
        public class KakaoAccount {
            @JsonProperty(value = "profile_nickname_needs_agreement")
            public Boolean profileNicknameNeedsAgreement;
            public Profile profile;

            @JsonProperty(value = "has_email")
            public Boolean hasEmail;

            @JsonProperty(value = "email_needs_agreement")
            public Boolean emailNeedsAgreement;

            @JsonProperty(value = "is_email_valid")
            public Boolean isEmailValid;

            @JsonProperty(value = "is_email_verified")
            public Boolean isEmailVerified;
            public String email;

            @Data
            public class Profile {
                public String nickname;
            }
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class SignInDto {

        @NotNull
        @Email
        private String email;

        @NotNull
        @Size(min = 3, max = 100)
        private String password;
    }
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class SignUpDto {

        @NotNull
        @Email
        private String email;

        @NotNull
        @Size(min = 3, max = 100)
        private String password;

        private LoginType type = LoginType.CUSTOM;
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomOption {
        @NotNull
        private String nickname;

        @NotNull
        private User.Gender gender;

        private List<String> style;
    }

}
