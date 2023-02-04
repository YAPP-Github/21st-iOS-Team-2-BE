package com.yapp.ios2.fitfty.interfaces.board;

import com.yapp.ios2.fitfty.domain.board.Board.CloudType;
import com.yapp.ios2.fitfty.domain.tag.TagGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.List;

public class BoardDto {
    @Getter
    @Setter
    @ToString
    public static class RegisterBoardRequest {
        private String filePath;
        private String content;
        private Float temperature;
        private String location;
        private CloudType cloudType;
        private ZonedDateTime photoTakenTime;
        private RegisterTagGroupRequest tagGroup;
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterTagGroupRequest {
        private TagGroup.Weather weather;
        private List<TagGroup.Style> style;
        private TagGroup.Gender gender;
    }

    @Getter
    @Setter
    @ToString
    public static class GetPictureRequest {
        private String weather;
    }

    @Getter
    @Builder
    @ToString
    public static class PictureListResponse {
        private final List<StyleInfo> styleInfoList;
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterResponse {
        private String content;
        private Float temperature;
        private String location;
        private CloudType cloudType;
        private ZonedDateTime photoTakenTime;
    }

    @Getter
    @Builder
    @ToString
    public static class Main {
        private final String boardToken;
        private final String nickname;
        private final String filePath;
        private final String content;
        private final String location;
        private final Float temperature;
        private final CloudType cloudType;
        private final ZonedDateTime photoTakenTime;
        private final Integer views;
        private final Integer bookmarkCnt;
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
