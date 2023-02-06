package com.yapp.ios2.fitfty.infrastructure.tag;

import com.yapp.ios2.fitfty.domain.tag.TagGroup;
import com.yapp.ios2.fitfty.domain.tag.TagGroupStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TagGroupStoreImpl implements TagGroupStore {
    private final TagGroupRepository tagGroupRepository;

    @Override
    public TagGroup store(TagGroup tagGroup) {
        return tagGroupRepository.save(tagGroup);
    }

    @Override
    public void deleteTagGroup(TagGroup tagGroup) {
        tagGroupRepository.delete(tagGroup);
    }
}
