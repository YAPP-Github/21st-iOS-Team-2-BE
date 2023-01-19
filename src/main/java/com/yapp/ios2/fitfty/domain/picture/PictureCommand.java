package com.yapp.ios2.fitfty.domain.picture;

import com.yapp.ios2.fitfty.domain.tag.Tag;
import com.yapp.ios2.fitfty.domain.tag.TagGroup;
import com.yapp.ios2.fitfty.domain.picture.Board.WeatherType;
import com.yapp.ios2.fitfty.interfaces.picture.BoardDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.List;

public class PictureCommand {

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

        public Board toEntity(Long userId, Picture picture) {
            return Board.builder()
                    .userId(userId)
                    .picture(picture)
                    .content(content)
                    .location(location)
                    .temperature(temperature)
                    .weather(weather)
                    .photoTakenTime(photoTakenTime)
                    .build();
        }

        public Picture toPictureEntity(Long userId) {
            return Picture.builder()
                    .userId(userId)
                    .filePath(filePath)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterTagGroupRequest {  // ex) style
        private final String tagGroupName;
        private final List<RegisterTagRequest> registerTagRequestList; // ex) formal, casual

        public TagGroup toEntity(Picture picture) {
            return TagGroup.builder()
                    .picture(picture)
                    .tagGroupName(tagGroupName)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterTagRequest {
        private final String tagValue;

        public Tag toEntity(TagGroup tagGroup) {
            return Tag.builder()
                    .tagGroup(tagGroup)
                    .tagValue(tagValue)
                    .build();
        }
    }
}
