package com.yapp.ios2.fitfty.infrastructure.tag;

import com.yapp.ios2.fitfty.domain.tag.Tag;
import com.yapp.ios2.fitfty.domain.tag.TagStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TagStoreImpl implements TagStore {
    private final TagRepository tagRepository;
    
    @Override
    public Tag store(Tag tag) {
        return tagRepository.save(tag);
    }
}
