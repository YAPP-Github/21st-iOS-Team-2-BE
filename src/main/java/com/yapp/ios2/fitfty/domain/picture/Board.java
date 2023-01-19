package com.yapp.ios2.fitfty.domain.picture;

import com.yapp.ios2.fitfty.domain.AbstractEntity;
import com.yapp.ios2.fitfty.global.exception.InvalidParamException;
import com.yapp.ios2.fitfty.global.util.TokenGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "board")
public class Board extends AbstractEntity {
    private static final String BOARD_PREFIX = "brd_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String boardToken;
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picture_id")
    private Picture picture;

    @Lob
    private String content;
    private String location;
    private Float temperature;

    @Enumerated(EnumType.STRING)
    private WeatherType weather;
    private ZonedDateTime photoTakenTime; //data 형식 변경 여부 확인

    @Builder
    public Board(Long userId, Picture picture, String content, String location,
                 Float temperature, WeatherType weather, ZonedDateTime photoTakenTime) {
        if (userId == null) {
            throw new InvalidParamException("Board.userId");
        }
        if (picture == null) {
            throw new InvalidParamException("Board.picture");
        }

        this.boardToken = TokenGenerator.randomCharacterWithPrefix(BOARD_PREFIX);
        this.userId = userId;
        this.picture = picture;
        this.content = content;
        this.location = location;
        this.temperature = temperature;
        this.weather = weather;
        this.photoTakenTime = photoTakenTime;
    }

    public void changePicture(Picture picture) {
        this.picture = picture;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeLocation(String location) {
        this.location = location;
    }

    public void changeTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public void changeWeatherType(WeatherType weather) {
        this.weather = weather;
    }

    public void changePhotoTakenTime(ZonedDateTime photoTakenTime) {
        this.photoTakenTime = photoTakenTime;
    }

    @Getter
    @RequiredArgsConstructor
    public enum WeatherType {
        SUNNY("맑음"),
        CLOUDY("구름많음"),
        GRAY("흐림");
        private final String description;
    }

}
