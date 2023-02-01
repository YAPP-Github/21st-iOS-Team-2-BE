package com.yapp.ios2.fitfty.domain.board;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class PictureInfo {

    @Getter
    @Builder
    @ToString
    public static class Main {
        private final List<StyleInfo> styleInfoList;
    }

    @Getter
    @Builder
    @ToString
    public static class StyleInfo {
        private final String style;
        private final List<PictureDetailInfo> pictureInfoList;
    }

    @Getter
    @Builder
    @ToString
    public static class PictureDetailInfo {
        private final String filePath;
        private final String nickname;
        private final Integer views;
        private final Boolean bookmarked;
    }
}
