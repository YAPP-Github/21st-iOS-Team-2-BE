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
        private final String filePath;
        private final String content;
        private final String location;
        private final Float temperature;
        private final Board.WeatherType weather;
        private final ZonedDateTime photoTakenTime;
        private final Integer bookmarkCnt;
    }

    @Getter
    @Builder
    @ToString
    public static class PictureInfo {
        private final String pictureToken;
        private final String userToken;
        private final String filePath;
        private final List<TagGroupInfo> tagGroupList;
    }

    @Getter
    @Builder
    @ToString
    public static class TagGroupInfo {
        private final String tagGroupName;
        private final String tagValue;
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
