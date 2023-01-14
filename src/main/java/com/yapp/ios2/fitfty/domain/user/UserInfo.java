package com.yapp.ios2.fitfty.domain.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class UserInfo {

    @Getter
    @Setter
    @Builder
    public static class Login {
        @NotNull
        @Size(min = 3, max = 50)
        private String email;

//        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//        @NotNull
//        @Size(min = 3, max = 100)
//        private String password;

        @NotNull
        @Size(min = 3, max = 50)
        private String nickname;
    }

    public static Login from(User user) {
        if(user == null) return null;

        return Login.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }

}
