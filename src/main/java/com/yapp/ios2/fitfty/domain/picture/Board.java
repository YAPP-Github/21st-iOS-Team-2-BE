package com.yapp.ios2.fitfty.domain.picture;

import com.yapp.ios2.fitfty.domain.AbstractEntity;
import com.yapp.ios2.fitfty.global.exception.InvalidParamException;
import com.yapp.ios2.fitfty.global.util.BooleanToYNConverter;
import com.yapp.ios2.fitfty.global.util.TokenGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
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
@Table(name = "`board`")
public class Board extends AbstractEntity {
    private static final String BOARD_PREFIX = "brd_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String boardToken;
    private String userToken;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "picture_id")
    private Picture picture;

    @Lob
    private String content;
    private String location;
    private Float temperature;

    @Enumerated(EnumType.STRING)
    private WeatherType weather;
    private ZonedDateTime photoTakenTime; //data 형식 변경 여부 확인
    private Integer bookmarkCnt;

    @Convert(converter = BooleanToYNConverter.class)
    private boolean status;

    @Builder
    public Board(String userToken, Picture picture, String content, String location,
                 Float temperature, WeatherType weather, ZonedDateTime photoTakenTime) {
        if (StringUtils.isBlank(userToken)) {
            throw new InvalidParamException("Board.userToken");
        }
        if (picture == null) {
            throw new InvalidParamException("Board.picture");
        }

        this.boardToken = TokenGenerator.randomCharacterWithPrefix(BOARD_PREFIX);
        this.userToken = userToken;
        this.picture = picture;
        this.content = content;
        this.location = location;
        this.temperature = temperature;
        this.weather = weather;
        this.photoTakenTime = photoTakenTime;
        this.bookmarkCnt = 0;
        this.status = true;
    }

    public void update(PictureCommand.RegisterBoardRequest request,
                       Picture picture) {
        this.picture = picture;
        this.content = request.getContent();
        this.location = request.getLocation();
        this.temperature = request.getTemperature();
        this.weather = request.getWeather();
        this.photoTakenTime = request.getPhotoTakenTime();
    }

    public void deleteBoard() {
        this.status = false;
    }

    public void increaseBookmarkCnt() {
        this.bookmarkCnt += 1;
    }

    public void decreaseBookmarkCnt() {
        this.bookmarkCnt -= 1;
    }

    @Getter
    @RequiredArgsConstructor
    public enum WeatherType {
        SUNNY("맑음"), CLOUDY("구름많음"), GRAY("흐림");
        private final String description;
    }

}
