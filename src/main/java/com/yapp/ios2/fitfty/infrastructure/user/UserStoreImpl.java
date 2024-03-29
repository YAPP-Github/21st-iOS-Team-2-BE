package com.yapp.ios2.fitfty.infrastructure.user;

import com.yapp.ios2.fitfty.domain.user.Bookmark;
import com.yapp.ios2.fitfty.domain.user.Feed;
import com.yapp.ios2.fitfty.domain.user.User;
import com.yapp.ios2.fitfty.domain.user.UserStore;
import com.yapp.ios2.fitfty.global.exception.DuplicateException;
import com.yapp.ios2.fitfty.global.exception.EntityNotFoundException;
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
        if (bookmarkRepository.findFirstByUserTokenAndBoardToken(bookmark.getUserToken(),
                                                                 bookmark.getBoardToken())
                .orElse(null) != null) {
            throw new DuplicateException();
        }
        return bookmarkRepository.save(bookmark);
    }

    @Override
    public Feed store(Feed feed) {
        if (feedRepository.findFirstByUserTokenAndBoardToken(feed.getUserToken(),
                                                             feed.getBoardToken())
                .orElse(null) != null) {
            throw new DuplicateException();
        }
        return feedRepository.save(feed);
    }

    @Override
    public void deleteFeedByUserTokenAndBoardToken(String userToken, String boardToken) {
        var feed = feedRepository.findFirstByUserTokenAndBoardToken(userToken, boardToken)
                .orElseThrow(EntityNotFoundException::new);
        feedRepository.delete(feed);
    }

    @Override
    public void deleteBookmarkByUserTokenAndBoardToken(String userToken, String boardToken) {
        var bookmark = bookmarkRepository.findFirstByUserTokenAndBoardToken(userToken, boardToken)
                .orElseThrow(EntityNotFoundException::new);
        bookmarkRepository.delete(bookmark);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void deleteAllBookmark(String boardToken) {
        var bookmarks = bookmarkRepository.findAllByBoardToken(boardToken);
        bookmarkRepository.deleteAll(bookmarks);
    }
}
