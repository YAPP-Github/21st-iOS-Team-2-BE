package com.yapp.ios2.fitfty.domain.picture;

import com.google.common.collect.Lists;
import com.yapp.ios2.fitfty.domain.AbstractEntity;
import com.yapp.ios2.fitfty.domain.tag.TagGroup;
import com.yapp.ios2.fitfty.global.exception.InvalidParamException;
import com.yapp.ios2.fitfty.global.util.TokenGenerator;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "picture")
public class Picture extends AbstractEntity {
    private static final String ITEM_PREFIX = "pic_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pictureToken;
    private Long userId;
    private String filePath;
    private Integer bookmarkCnt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "picture", cascade = CascadeType.PERSIST)
    private List<TagGroup> tagGroupList = Lists.newArrayList();

    @Builder
    public Picture(Long userId, String filePath) {
        if (userId == null) {
            throw new InvalidParamException("Picture.userId");
        }
        if (StringUtils.isBlank(filePath)) {
            throw new InvalidParamException("Picture.filePath");
        }

        this.pictureToken = TokenGenerator.randomCharacterWithPrefix(ITEM_PREFIX);
        this.userId = userId;
        this.filePath = filePath;
        this.bookmarkCnt = 0;
    }

    public void increaseBookmarkCnt() {
        this.bookmarkCnt += 1;
    }

}
