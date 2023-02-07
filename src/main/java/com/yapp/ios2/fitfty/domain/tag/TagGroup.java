package com.yapp.ios2.fitfty.domain.tag;

import com.yapp.ios2.fitfty.domain.AbstractEntity;
import com.yapp.ios2.fitfty.domain.board.BoardCommand;
import com.yapp.ios2.fitfty.domain.board.Picture;
import com.yapp.ios2.fitfty.domain.user.Utils.StringListConverter;
import com.yapp.ios2.fitfty.global.exception.InvalidParamException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "`tag_group`")
public class TagGroup extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @Setter
    @JoinColumn(name = "picture_id")
    private Picture picture;

    private String weather;

    @Convert(converter = StringListConverter.class)
    private List<String> style;

    private String gender;

    @Builder
    public TagGroup(Picture picture, String weather, List<String> style, String gender) {
        if (picture == null) {
            throw new InvalidParamException("TagGroup.picture");
        }
        this.picture = picture;
        this.weather = weather;
        this.style = style;
        this.gender = gender;
    }

    public Long getPictureId() {
        return picture.getId();
    }

    public void update(BoardCommand.RegisterBoardRequest request) {
        this.weather = request.getRegisterTagGroupRequest()
                .getWeather();
        this.style = request.getRegisterTagGroupRequest()
                .getStyle();
        this.gender = request.getRegisterTagGroupRequest()
                .getGender();
    }

    @Getter
    @RequiredArgsConstructor
    public enum Gender {
        FEMALE("여성"),
        MALE("남성");

        private final String description;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Weather {
        FREEZING("한파"),
        COLD("추운 날"),
        CHILLY("쌀쌀한 날"),
        WARM("따뜻한 날"),
        HOT("더운 날");

        private final String description;
    }
}
