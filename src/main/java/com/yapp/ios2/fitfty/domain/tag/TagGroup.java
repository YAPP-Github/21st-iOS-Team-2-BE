package com.yapp.ios2.fitfty.domain.tag;

import com.google.common.collect.Lists;
import com.yapp.ios2.fitfty.domain.AbstractEntity;
import com.yapp.ios2.fitfty.domain.picture.Picture;
import com.yapp.ios2.fitfty.global.exception.InvalidParamException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "tag_group")
public class TagGroup extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "picture_id")
    private Picture picture;
    private String tagGroupName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tagGroup", cascade = CascadeType.PERSIST)
    private List<Tag> tagList = Lists.newArrayList();

    @Builder
    public TagGroup(Picture picture, String tagGroupName) {
        if (picture == null) {
            throw new InvalidParamException("TagGroup.picture");
        }
        if (StringUtils.isBlank(tagGroupName)) {
            throw new InvalidParamException("TagGroup.tagGroupName");
        }

        this.picture = picture;
        this.tagGroupName = tagGroupName;
    }

    public TagGroup addTag(Tag tag) {
        this.tagList.add(tag);
        return this;
    }
}
