package com.yapp.ios2.fitfty.domain.picture;

import com.google.common.collect.Lists;
import com.yapp.ios2.fitfty.domain.AbstractEntity;
import com.yapp.ios2.fitfty.domain.user.taggroup.TagGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
@Table(name = "pictures")
public class Picture extends AbstractEntity {
    private static final String ITEM_PREFIX = "pic_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pictureToken;
    private Long userId;
    private Long boardId;
    private String filePath;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "picture", cascade = CascadeType.PERSIST)
    private List<TagGroup> tagOptionGroupList = Lists.newArrayList();

}
