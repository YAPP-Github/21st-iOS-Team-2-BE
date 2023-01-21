package com.yapp.ios2.fitfty.interfaces.picture;

import com.yapp.ios2.fitfty.domain.picture.Board;
import com.yapp.ios2.fitfty.domain.picture.Picture;
import com.yapp.ios2.fitfty.domain.picture.Board.WeatherType;
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
        private WeatherType weather;
        private ZonedDateTime photoTakenTime;
        private List<RegisterTagGroupRequest> tagGroupList;
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterTagGroupRequest {
        private String tagGroupName;
        private List<RegisterTagRequest> tagList;
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterTagRequest {
        private String tagValue;
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterResponse {
        private String content;
        private Float temperature;
        private String location;
        private WeatherType weather;
        private ZonedDateTime photoTakenTime;
        private Picture picture;
    }

    @Getter
    @Builder
    @ToString
    public static class Main {
        private final String boardToken;
        private final String userToken;
        private final String filePath;
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
        private final String userToken;
        private final String pictureName;
        private final Long picturePrice;
        private final List<TagGroupInfo> itemOptionGroupList;
    }

    @Getter
    @Builder
    @ToString
    public static class TagGroupInfo {
        private final String tagGroupType;
        private final List<TagInfo> tagList;
    }

    @Getter
    @Builder
    @ToString
    public static class TagInfo {
        private final String tagValue;
    }

}
