package com.yapp.ios2.fitfty.interfaces.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoProfileDto {
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