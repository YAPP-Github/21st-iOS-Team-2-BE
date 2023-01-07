package com.yapp.ios2.fitfty.domain.user.tag;

import com.yapp.ios2.fitfty.domain.AbstractEntity;
import com.yapp.ios2.fitfty.domain.user.taggroup.TagGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
@Table(name = "tags")
public class Tag extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tag_group_id")
    private TagGroup tagGroup;
    private String tagName;
}
