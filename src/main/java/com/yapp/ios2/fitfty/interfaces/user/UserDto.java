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
        @JsonIgnoreProperties(ignoreUnknown = true)
        public class Properties {
            public String nickname;
            @JsonProperty(value = "profile_image")
            public String profileImage;
            @JsonProperty(value = "thumbnail_image")
            public String thumbnailImage;
        }

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public class KakaoAccount {
            @JsonProperty(value = "profile_nickname_needs_agreement")
            public Boolean profileNicknameNeedsAgreement;
            @JsonProperty(value = "profile_image_needs_agreement")
            public Boolean profileImageNeedsAgreement;
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

            @JsonProperty(value = "has_birthday")
            public Boolean hasBirthday;
            @JsonProperty(value = "birthday_needs_agreement")
            public Boolean birthdayNeedsAgreement;
            public String birthday;
            @JsonProperty(value = "birthday_type")
            public String birthdayType;

            @JsonProperty(value = "has_gender")
            public Boolean hasGender;
            @JsonProperty(value = "gender_needs_agreement")
            public Boolean genderNeedsAgreement;
            public String gender;
            @Data
            @JsonIgnoreProperties(ignoreUnknown = true)
            public class Profile {
                public String nickname;
                @JsonProperty(value = "profile_image_url")
                public String profileImageUrl;
                @JsonProperty(value = "thumbnail_image_url")
                public String thumbnailImageUrl;
                @JsonProperty(value = "is_default_image")
                public Boolean isDefaultImage;
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

        @Builder.Default
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

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomPrivacy {
        @NotNull
        private String phoneNumber;
        @NotNull
        private User.Gender gender;
        @NotNull
        private String nickname;
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AppleSignInDto {
        String userIdentifier;
        String userEmail;
        String userName;
        @NotNull
        String identityToken;
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class KakaoSignInDto {
        @NotNull
        String accessToken;
    }
}
