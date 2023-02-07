package com.yapp.ios2.fitfty.domain.board;

import com.yapp.ios2.fitfty.domain.AbstractEntity;
import com.yapp.ios2.fitfty.global.exception.InvalidParamException;
import com.yapp.ios2.fitfty.global.util.TokenGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.CascadeType;
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "picture_id")
    private Picture picture;

    @Lob
    private String content;
    private String location;
    private Float temperature;

    @Enumerated(EnumType.STRING)
    private CloudType cloudType;
    private ZonedDateTime photoTakenTime;
    private Integer views;
    private Integer bookmarkCnt;

    @Builder
    public Board(String userToken, Picture picture, String content, String location,
                 Float temperature, CloudType cloudType, ZonedDateTime photoTakenTime) {
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
        this.cloudType = cloudType;
        this.photoTakenTime = photoTakenTime;
        this.views = 0;
        this.bookmarkCnt = 0;
    }

    public void update(BoardCommand.RegisterBoardRequest request, Picture picture) {
        this.picture = picture;
        this.content = request.getContent();
        this.location = request.getLocation();
        this.temperature = request.getTemperature();
        this.cloudType = request.getCloudType();
        this.photoTakenTime = request.getPhotoTakenTime();
    }

    public void increaseViews() {
        this.views += 1;
    }

    public void increaseBookmarkCnt() {
        this.bookmarkCnt += 1;
    }

    public void decreaseBookmarkCnt() {
        this.bookmarkCnt -= 1;
    }

    @Getter
    @RequiredArgsConstructor
    public enum CloudType {
        SUNNY("맑음"),
        LOTSOFCLOUDY("구름많음"),
        CLOUDY("흐림"),
        RAIN("비"),
        RAINORSNOW("비 또는 눈"),
        SNOW("눈"),
        SCURRY("소나기");
        private final String description;
    }

}
