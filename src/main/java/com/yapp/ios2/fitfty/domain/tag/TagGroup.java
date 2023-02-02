package com.yapp.ios2.fitfty.domain.tag;

import com.yapp.ios2.fitfty.domain.AbstractEntity;
import com.yapp.ios2.fitfty.domain.board.Picture;
import com.yapp.ios2.fitfty.domain.user.Utils.StringListConverter;
import com.yapp.ios2.fitfty.global.exception.InvalidParamException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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

    @ManyToOne
    @Setter
    @JoinColumn(name = "picture_id")
    private Picture picture;
    // private String tagGroupName;
    private String weather;

    @Convert(converter = StringListConverter.class)
    private List<String> style;

    // @Builder
    // public TagGroup(Picture picture, String tagGroupName, String tagValue) {
    // if (picture == null) {
    // throw new InvalidParamException("TagGroup.picture");
    // }
    // if (StringUtils.isBlank(tagGroupName)) {
    // throw new InvalidParamException("TagGroup.tagGroupName");
    // }

    // this.picture = picture;
    // this.tagGroupName = tagGroupName;
    // this.tagValue = tagValue;
    // }

    @Builder
    public TagGroup(Picture picture, String weather, List<String> style) {
        if (picture == null) {
            throw new InvalidParamException("TagGroup.picture");
        }
        this.picture = picture;
        this.weather = weather;
        this.style = style;
    }

    public Long getPictureId() {
        return picture.getId();
    }
}
