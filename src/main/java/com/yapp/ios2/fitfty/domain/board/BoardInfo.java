package com.yapp.ios2.fitfty.domain.board;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.List;

public class BoardInfo {

    @Getter
    @Builder
    @ToString
    public static class Main {
        private final Long boardId;
        private final String boardToken;
        private final String userToken;
        private final String nickname;
        private final String profilePictureUrl;
        private final String filePath;
        private final String content;
        private final TagGroupInfo tagGroupInfo;
        private final String location;
        private final Float temperature;
        private final Board.CloudType cloudType;
        private final ZonedDateTime photoTakenTime;
        private final ZonedDateTime createdAt;
        private final Integer views;
        private final Integer bookmarkCnt;
        private final Boolean bookmarked;
    }

    @Getter
    @Builder
    @ToString
    public static class TagGroupInfo {
        private final String weather;
        private final List<String> style;
        private final String gender;
    }

    @Getter
    @Builder
    @ToString
    public static class PicturePathInfo {
        private final String pictureToken;
        private final String userToken;
        private final String filePath;
    }
}
