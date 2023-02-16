package com.yapp.ios2.fitfty.domain.user;

import com.yapp.ios2.fitfty.domain.board.Board;
import com.yapp.ios2.fitfty.domain.user.User.Gender;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class UserInfo {

    @Getter
    @Builder
    @ToString
    public static class CustomOption {

        private String email;
        private String userToken;
        private String nickname;
        private Gender gender;
        private List<String> style;
        private Boolean isOnBoardingComplete;
    }

    @Getter
    @Builder
    @ToString
    public static class CustomPrivacy {

        private String email;
        private String userToken;
        private String nickname;
        private Gender gender;
        private String birthday;
        private String role;
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    public static class ImageInfo {

        private String userToken;
        private String boardToken;
        private String filePath;

        public static ImageInfo of(Bookmark bookmark, Board board) {
            return new UserInfo.ImageInfo(board.getUserToken(), bookmark.getBoardToken(),
                                          board.getPicture()
                                                  .getFilePath());
        }

        public static ImageInfo of(Feed feed, Board board) {
            return new UserInfo.ImageInfo(board.getUserToken(), feed.getBoardToken(),
                                          board.getPicture()
                                                  .getFilePath());
        }
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
        private String userToken;
        private String nickname;
        private String profilePictureUrl;
        private String message;
        private List<ImageInfo> codiList;
        private List<ImageInfo> bookmarkList;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class SignInInfo {
        private String authToken;
        private Boolean isNew;
    }
}
