package com.yapp.ios2.fitfty.domain.picture;

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
        private final Long userId;
        private final Picture picture;
        private final String content;
        private final String location;
        private final Float temperature;
        private final Board.WeatherType weather;
        private final ZonedDateTime photoTakenTime;
    }

    @Getter
    @Builder
    @ToString
    public static class PictureInfo {
        private final String pictureToken;
        private final Long userId;
        private final String filePath;
        private final Integer bookmarkCnt;
        private final List<TagGroupInfo> tagGroupList;
    }

    @Getter
    @Builder
    @ToString
    public static class TagGroupInfo {
        private final String tagGroupName;
        private final List<TagInfo> tagList;
    }

    @Getter
    @Builder
    @ToString
    public static class TagInfo {
        private final String tagValue;
    }
}
