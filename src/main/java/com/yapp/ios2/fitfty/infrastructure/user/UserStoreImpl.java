package com.yapp.ios2.fitfty.infrastructure.user;

import com.yapp.ios2.fitfty.domain.user.Bookmark;
import com.yapp.ios2.fitfty.domain.user.Feed;
import com.yapp.ios2.fitfty.domain.user.User;
import com.yapp.ios2.fitfty.domain.user.UserStore;
import com.yapp.ios2.fitfty.global.exception.DuplicateException;
import com.yapp.ios2.fitfty.global.exception.EntityNotFoundException;
import com.yapp.ios2.fitfty.global.exception.MemberAlreadyExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {

    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final FeedRepository feedRepository;


    @Override
    public User store(User user) {
        return userRepository.save(user);
    }

    @Override
    public Bookmark store(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    @Override
    public Feed store(Feed feed) {
        if (feedRepository.findOneByUserTokenAndPictureToken(feed.getUserToken(),
                                                             feed.getPictureToken())
                .orElse(null) != null) {
            throw new DuplicateException();
        }
        return feedRepository.save(feed);
    }

    @Override
    public void deleteByUserTokenAndPictureToken(String userToken, String pictureToken) {
        var feed = feedRepository.findOneByUserTokenAndPictureToken(userToken, pictureToken)
                .orElseThrow(EntityNotFoundException::new);
        feedRepository.delete(feed);
    }
}
