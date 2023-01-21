package com.yapp.ios2.fitfty.infrastructure.user;

import com.yapp.ios2.fitfty.domain.user.Bookmark;
import com.yapp.ios2.fitfty.domain.user.Feed;
import com.yapp.ios2.fitfty.domain.user.User;
import com.yapp.ios2.fitfty.domain.user.UserReader;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {

    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    private final BookmarkRepository bookmarkRepository;

    @Override
    public Optional<User> findOneByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    @Override
    public Optional<User> findOneByUserToken(String userToken) {
        return userRepository.findOneByUserToken(userToken);
    }

    @Override
    public Optional<User> findOneByNickname(String nickname) {
        return userRepository.findOneByNickname(nickname);
    }

    @Override
    public List<Feed> findFeedByUserToken(String userToken) {
        return feedRepository.findByUserToken(userToken);
    }

    @Override
    public List<Bookmark> findBookmarkByUserToken(String userToken) {
        return bookmarkRepository.findByUserToken(userToken);
    }
}
