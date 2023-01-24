package com.yapp.ios2.fitfty.domain.board;

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
import java.util.stream.Collectors;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "`picture`")
public class Picture extends AbstractEntity {
    private static final String ITEM_PREFIX = "pic_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pictureToken;
    private String userToken;
    private String filePath;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "picture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TagGroup> tagGroupList = Lists.newArrayList();

    @Builder
    public Picture(String userToken, String filePath) {
        if (StringUtils.isBlank(userToken)) {
            throw new InvalidParamException("Picture.userId");
        }
        if (StringUtils.isBlank(filePath)) {
            throw new InvalidParamException("Picture.filePath");
        }

        this.pictureToken = TokenGenerator.randomCharacterWithPrefix(ITEM_PREFIX);
        this.userToken = userToken;
        this.filePath = filePath;
    }

    public void update(BoardCommand.RegisterBoardRequest request) {
        this.filePath = request.getFilePath();
        var registerTagGroupRequestList = request.getRegisterTagGroupRequestList();

        tagGroupList.clear();
        tagGroupList.addAll(registerTagGroupRequestList.stream()
                                    .map(requestTagGroup -> requestTagGroup.toEntity(this))
                                    .collect(Collectors.toList()));

    }
}
