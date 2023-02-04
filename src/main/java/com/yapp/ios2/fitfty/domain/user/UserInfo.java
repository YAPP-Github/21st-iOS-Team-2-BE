package com.yapp.ios2.fitfty.domain.user;

import com.yapp.ios2.fitfty.domain.user.User.Gender;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class UserInfo {

    @Getter
    @Builder
    @ToString
    public static class Login {

        @NotNull
        @Size(min = 3, max = 50)
        private String email;

        @NotNull
        @Size(min = 3, max = 50)
        private String nickname;
    }

    @Getter
    @Builder
    @ToString
    public static class CustomOption {

        private String email;
        private String nickname;
        private Gender gender;
        private List<String> style;
    }

    @Getter
    @Builder
    @ToString
    public static class CustomPrivacy {

        private String email;
        private String nickname;
        private String phoneNumber;
        private Gender gender;
    }

    @Getter
    @Builder
    @ToString
    public static class ImageInfo {

        private String userToken;
        private String boardToken;
    }

    @Getter
    @Builder
    @ToString
    public static class ProfileInfo {

        private String profilePictureUrl;
        private String message;
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    public static class UserProfile {

        private String nickname;
        private String profilePictureUrl;
        private String message;
        private List<ImageInfo> codiList;
        private List<ImageInfo> bookmarkList;
    }
}
