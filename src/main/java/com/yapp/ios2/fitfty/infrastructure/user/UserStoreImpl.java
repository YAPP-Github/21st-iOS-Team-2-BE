package com.yapp.ios2.fitfty.infrastructure.user;

import com.yapp.ios2.fitfty.domain.user.Bookmark;
import com.yapp.ios2.fitfty.domain.user.User;
import com.yapp.ios2.fitfty.domain.user.UserStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {

    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;


    @Override
    public User store(User user) {
        return userRepository.save(user);
    }

    @Override
    public Bookmark store(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }
}
