package com.yapp.ios2.fitfty.domain.board;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

public class PictureInfo {

    @Getter
    @Builder
    @ToString
    public static class Main {
        private final List<PictureDetailInfo> pictureDetailInfoList;
    }

    @Getter
    @ToString
    public static class PictureDetailInfo {
        private final String filePath;
        private final String boardToken;
        private final String nickname;
        private final Integer views;
        private final Boolean bookmarked;

        public PictureDetailInfo(String filePath, String boardToken, Integer views, String nickname, Boolean bookmarked) {
            this.filePath = filePath;
            this.boardToken = boardToken;
            this.nickname = nickname;
            this.views = views;
            this.bookmarked = bookmarked;
        }
    }
}
