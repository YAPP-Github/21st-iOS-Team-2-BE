package com.yapp.ios2.fitfty.domain.user;

import com.yapp.ios2.fitfty.domain.user.UserCommand.Profile;
import com.yapp.ios2.fitfty.domain.user.UserInfo.ProfileInfo;
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
    @Transactional
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
    @Transactional
    public String findNickname(String nickname) {

        Optional<User> result = userReader.findOneByNickname(nickname);

        if (result.orElse(null) == null) {
            return null;
        }
        return result.get()
                .getNickname();
    }

    @Override
    @Transactional
    public String findUserToken(String nickname) {
        Optional<User> result = userReader.findOneByNickname(nickname);

        if (result.orElse(null) == null) {
            return null;
        }
        return result.get()
                .getUserToken();
    }

    @Override
    @Transactional
    public UserInfo.CustomOption updateUserDetails(UserCommand.CustomOption command) {
        String userToken = getCurrentUserToken();
        User userInit = userReader.findOneByUserToken(userToken);
        userInit.updateCustomOption(command);
        return userMapper.toCustomOption(userStore.store(userInit));
    }

    @Override
    @Transactional
    public List<String> getBookmark(String userToken) {
        List<Bookmark> bookmark = userReader.findBookmarkByUserToken(userToken);
        return bookmark.stream().map(Bookmark::getBoardToken).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserInfo.ImageInfo addBookmark(UserCommand.Bookmark command) {
        var user = userReader.findOneByUserToken(command.getUserToken());
        var initBookmark = Bookmark.builder()
                .boardToken(command.getBoardToken())
                .userToken(command.getUserToken())
                .user(user)
                .build();

        var bookmark = userStore.store(initBookmark);

        return UserInfo.ImageInfo.builder()
                .boardToken(bookmark.getBoardToken())
                .userToken(bookmark.getUserToken())
                .build();
    }

    @Override
    @Transactional
    public void deleteBookmark(UserCommand.Bookmark command) {
        userStore.deleteBookmarkByUserTokenAndBoardToken(command.getUserToken(),
                                                     command.getBoardToken());
    }

    @Override
    @Transactional
    public List<String> getUserFeed(String userToken) {
        List<Feed> feed = userReader.findFeedByUserToken(userToken);
        return feed.stream().map(Feed::getBoardToken).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserInfo.ImageInfo addUserFeed(UserCommand.UserFeed command) {
        var user = userReader.findOneByUserToken(command.getUserToken());
        var initFeed = Feed.builder()
                .boardToken(command.getBoardToken())
                .userToken(command.getUserToken())
                .user(user)
                .build();

        var feed = userStore.store(initFeed);

        return UserInfo.ImageInfo.builder()
                .boardToken(feed.getBoardToken())
                .userToken(feed.getUserToken())
                .build();
    }

    @Override
    @Transactional
    public void deleteUserFeed(UserCommand.UserFeed command) {
        userStore.deleteFeedByUserTokenAndBoardToken(command.getUserToken(),
                                                     command.getBoardToken());
    }

    @Override
    @Transactional
    public UserInfo.UserProfile retrieveProfile(String nickname) {
        String userToken = (nickname == null) ? getCurrentUserToken() : findUserToken(nickname);
        var user = userReader.findOneByUserToken(userToken);
        var userFeed = user.getFeedList();
        var userBookmark = user.getBookmarkList();

        return UserInfo.UserProfile.builder()
                .nickname(user.getNickname())
                .profilePictureUrl(user.getProfilePictureUrl())
                .message(user.getMessage())
                .bookmarkList(userBookmark.stream().map(userMapper::toImageInfo).collect(Collectors.toList()))
                .codiList(userFeed.stream().map(userMapper::toImageInfo).collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public ProfileInfo updateProfile(Profile command) {
        String userToken = getCurrentUserToken();
        var user = userReader.findOneByUserToken(userToken);
        user.updateProfile(command);
        return userMapper.toProfileInfo(userStore.store(user));
    }
}