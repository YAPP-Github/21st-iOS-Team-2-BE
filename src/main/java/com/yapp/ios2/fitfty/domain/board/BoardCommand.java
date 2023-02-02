package com.yapp.ios2.fitfty.domain.board;

import com.yapp.ios2.fitfty.domain.board.Board.WeatherType;
import com.yapp.ios2.fitfty.domain.tag.TagGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.List;

public class BoardCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterBoardRequest {
        private final String filePath;
        private final String content;
        private final Float temperature;
        private final String location;
        private final WeatherType weather;
        private final ZonedDateTime photoTakenTime;
        private final List<RegisterTagGroupRequest> registerTagGroupRequestList; //ex) 날씨, 스타일

        public Board toEntity(String userToken, Picture picture) {
            return Board.builder()
                    .userToken(userToken)
                    .picture(picture)
                    .content(content)
                    .location(location)
                    .temperature(temperature)
                    .weather(weather)
                    .photoTakenTime(photoTakenTime)
                    .build();
        }

        public Picture toPictureEntity(String userToken) {
            return Picture.builder()
                    .userToken(userToken)
                    .filePath(filePath)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterTagGroupRequest {
//        private final String tagGroupName;  // ex) style, weather
//        private final String tagValue;  //ex) formal, casual

        private final String weather;
        private final List<String> style;

        public TagGroup toEntity(Picture picture) {
            return TagGroup.builder()
                    .picture(picture)
                    .weather(weather)
                    .style(style)
                    .build();

//            return TagGroup.builder()
//                    .picture(picture)
//                    .tagGroupName(tagGroupName)
//                    .tagValue(tagValue)
//                    .build();
        }
    }
}
