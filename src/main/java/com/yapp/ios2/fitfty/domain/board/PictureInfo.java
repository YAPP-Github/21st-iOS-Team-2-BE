package com.yapp.ios2.fitfty.domain.board;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.lang.annotation.Repeatable;
import java.util.List;

public class PictureInfo {

    @Getter
    @Builder
    @ToString
    public static class Main {
        private final List<StyleInfo> styleInfoList;
    }

    @Getter
    // @Builder
    @ToString
    @RequiredArgsConstructor
    public static class StyleInfo {
        private final String style;
        private final List<PictureDetailInfo> pictureInfoList;
    }

    @Getter
    @ToString
    public static class PictureDetailInfo {
        private final String filePath;
        private final String nickname;
        private final Integer views;
        private final Boolean bookmarked;

        public PictureDetailInfo(Board board, String nickname, Boolean bookmarked) {
            this.filePath = board.getPicture().getFilePath();
            this.nickname = nickname;
            this.views = board.getViews();
            this.bookmarked = bookmarked;
        }
    }
}
