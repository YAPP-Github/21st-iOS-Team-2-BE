package com.yapp.ios2.fitfty.domain.user;

import com.yapp.ios2.fitfty.global.exception.CurrentContextError;
import com.yapp.ios2.fitfty.global.exception.MemberAlreadyExistException;
import com.yapp.ios2.fitfty.global.exception.MemberNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserReader userReader;
    private final UserStore userStore;
    private final UserMapper userMapper;

    @Override
    public String getCurrentUserToken() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new CurrentContextError();
        }

        return userReader.findOneByEmail(authentication.getName())
                .orElseThrow(() -> new MemberNotFoundException())
                .getUserToken();
    }

    @Override
    @Transactional
    public String registerUser(UserCommand.SignUp command) {
        if (userReader.findOneByEmail(command.getEmail())
                .orElse(null) != null) {
            throw new MemberAlreadyExistException();
        }

        User user = User.builder()
                .email(command.getEmail())
                .type(command.getType())
                .build();

        return userStore.store(user)
                .getUserToken();
    }

    @Override
    public String findNickname(String nickname) {

        Optional<User> result = userReader.findOneByNickname(nickname);

        if (result.orElse(null) == null) {
            return null;
        }
        return result.get()
                .getNickname();
    }

    @Override
    public UserInfo.CustomOption updateUserDetails(UserCommand.CustomOption command) {
        String userToken = getCurrentUserToken();
        User userInit = userReader.findOneByUserToken(userToken).orElseThrow(()-> new MemberNotFoundException());
        userInit.updateCustomOption(command);
        return userMapper.toCustomOption(userStore.store(userInit));
    }

    @Override
    public List<String> getBookmark(String userToken) {
        List<Bookmark> bookmark = userReader.findBookmarkByUserToken(userToken);
        return bookmark.stream().map(Bookmark::getBoardToken).collect(Collectors.toList());
    }

    @Override
    public UserInfo.Bookmark addBookmark(UserCommand.Bookmark command) {
        var initBookmark = Bookmark.builder()
                .boardToken(command.getBoardToken())
                .userToken(command.getUserToken())
                .build();

        var bookmark = userStore.store(initBookmark);

        return UserInfo.Bookmark.builder()
                .boardToken(bookmark.getBoardToken())
                .userToken(bookmark.getUserToken())
                .build();
    }

    @Override
    public void deleteBookmark(UserCommand.Bookmark command) {
        userStore.deleteBookmarkByUserTokenAndBoardToken(command.getUserToken(),
                                                     command.getBoardToken());
    }

    @Override
    public List<String> getUserFeed(String userToken) {
        List<Feed> feed = userReader.findFeedByUserToken(userToken);
        return feed.stream().map(Feed::getBoardToken).collect(Collectors.toList());
    }

    @Override
    public UserInfo.UserFeed addUserFeed(UserCommand.UserFeed command) {
        var initFeed = Feed.builder()
                .boardToken(command.getBoardToken())
                .userToken(command.getUserToken())
                .build();

        var feed = userStore.store(initFeed);

        return UserInfo.UserFeed.builder()
                .boardToken(feed.getBoardToken())
                .userToken(feed.getUserToken())
                .build();
    }

    @Override
    public void deleteUserFeed(UserCommand.UserFeed command) {
        userStore.deleteFeedByUserTokenAndBoardToken(command.getUserToken(),
                                                     command.getBoardToken());
    }
}