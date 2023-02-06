package com.yapp.ios2.fitfty.domain.board;

import com.yapp.ios2.fitfty.domain.tag.TagGroup;
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
        private final String nickname;
        private final String filePath;
        private final String content;
        private final String location;
        private final Float temperature;
        private final Board.CloudType cloudType;
        private final ZonedDateTime photoTakenTime;
        private final Integer views;
        private final Integer bookmarkCnt;
    }

    @Getter
    @Builder
    @ToString
    public static class TagGroupInfo {
        private final TagGroup.Weather weather;
        private final List<String> style;
        private final TagGroup.Gender gender;
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
