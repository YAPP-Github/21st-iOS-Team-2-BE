package com.yapp.ios2.fitfty.domain.user;

import com.yapp.ios2.fitfty.domain.user.User.Gender;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
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

        @NotNull
        @Size(min = 3, max = 50)
        private String nickname;
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

    @Getter
    @Setter
    @Builder
    public static class ImageInfo {

        private String userToken;
        private String boardToken;
    }

    @Getter
    @Setter
    @Builder
    public static class ProfileInfo {

        private String profilePictureUrl;
        private String message;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class UserProfile {

        private String nickname;
        private String profilePictureUrl;
        private String message;
        private List<ImageInfo> codiList;
        private List<ImageInfo> bookmarkList;
    }
}
