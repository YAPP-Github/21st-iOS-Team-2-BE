package com.yapp.ios2.fitfty.domain.user;

import com.yapp.ios2.fitfty.domain.user.User.Gender;
import java.util.List;
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

    public static Login toLogin(User user) {
        if(user == null) return null;

        return Login.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }

    @Getter
    @Setter
    @Builder
    public static class CustomOption {
        private String email;
        private String nickname;
        private Gender gender;
        private List<String> style;
    }

    public static CustomOption toCustomOption(User user) {
        if(user == null) return null;

        return CustomOption.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .gender(user.getGender())
                .style(user.getStyle())
                .build();
    }
}
