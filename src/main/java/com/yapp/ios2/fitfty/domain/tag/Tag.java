package com.yapp.ios2.fitfty.domain.tag;

import com.yapp.ios2.fitfty.domain.AbstractEntity;
import com.yapp.ios2.fitfty.global.exception.InvalidParamException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "`tag`")
public class Tag extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tag_group_id")
    private TagGroup tagGroup;
    private String tagValue;

    @Builder
    public Tag(TagGroup tagGroup, String tagValue) {
        if (tagGroup == null) {
            throw new InvalidParamException("Tag.tagGroup");
        }
        if (StringUtils.isBlank(tagValue)) {
            throw new InvalidParamException("Tag.tagValue");
        }

        this.tagGroup = tagGroup;
        this.tagValue = tagValue;
    }
}
