package com.yapp.ios2.fitfty.domain.user;

import com.yapp.ios2.fitfty.domain.board.BoardReader;
import com.yapp.ios2.fitfty.domain.user.UserCommand.Profile;
import com.yapp.ios2.fitfty.global.exception.CurrentContextError;
import com.yapp.ios2.fitfty.global.exception.DuplicateException;
import com.yapp.ios2.fitfty.global.exception.MemberAlreadyExistException;
import com.yapp.ios2.fitfty.global.exception.MemberNotFoundException;

import java.util.List;
import java.util.Objects;
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
    private final BoardReader boardReader;

    @Override
    @Transactional
    public String checkNonMemeber() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return "NONMEMBER";
        }

        return userReader.findFirstByEmail(authentication.getName())
                .orElseThrow(MemberNotFoundException::new)
                .getUserToken();
    }

    @Override
    @Transactional
    public String getCurrentUserToken() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new CurrentContextError();
        }

        return userReader.findFirstByEmail(authentication.getName())
                .orElseThrow(MemberNotFoundException::new)
                .getUserToken();
    }

    @Override
    @Transactional
    public User registerUser(UserCommand.SignUp command) {
        if (userReader.findFirstByEmail(command.getEmail())
                .orElse(null) != null) {
            throw new MemberAlreadyExistException();
        }

        var initUser = User.builder()
                .email(command.getEmail())
                .type(command.getType())
                .build();

        return userStore.store(initUser);
    }

    @Override
    @Transactional
    public Boolean findNickname(String nickname) {
        return userReader.findFirstByNickname(nickname)
                .isPresent();
    }

    @Override
    @Transactional
    public String findUserToken(String nickname) {
        var result = userReader.findFirstByNickname(nickname);

        return result.map(User::getUserToken)
                .orElse(null);
    }

    @Override
    @Transactional
    public UserInfo.CustomOption getUserDetail() {
        var userToken = getCurrentUserToken();
        var user = userReader.findFirstByUserToken(userToken);
        return userMapper.toCustomOption(user);
    }

    @Override
    @Transactional
    public UserInfo.CustomOption updateUserDetails(UserCommand.CustomOption command) {
        var userToken = getCurrentUserToken();
        var user = userReader.findFirstByUserToken(userToken);
        nicknameValidation(user, command.getNickname());
        user.updateCustomOption(command);
        return userMapper.toCustomOption(user);
    }

    private void nicknameValidation(User user, String newNickname) {
        if (!Objects.equals(user.getNickname(), newNickname)) {
            if (findNickname(newNickname)) {
                throw new DuplicateException("해당 닉네임이 이미 존재합니다.");
            }
        }
    }

    @Override
    @Transactional
    public UserInfo.CustomPrivacy getUserPrivacy() {
        var userToken = getCurrentUserToken();
        var user = userReader.findFirstByUserToken(userToken);
        return userMapper.toCustomPrivacy(user);
    }

    @Override
    @Transactional
    public UserInfo.CustomPrivacy updateUserPrivacy(UserCommand.CustomPrivacy command) {
        var userToken = getCurrentUserToken();
        var user = userReader.findFirstByUserToken(userToken);
        nicknameValidation(user, command.getNickname());
        user.updatePrivacyOption(command);
        return userMapper.toCustomPrivacy(user);
    }

    @Override
    @Transactional
    public List<String> getBookmark(String userToken) {
        List<Bookmark> bookmark = userReader.findBookmarkByUserToken(userToken);
        return bookmark.stream()
                .map(Bookmark::getBoardToken)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserInfo.ImageInfo addBookmark(UserCommand.Bookmark command) {
        var user = userReader.findFirstByUserToken(command.getUserToken());
        var initBookmark = Bookmark.builder()
                .boardToken(command.getBoardToken())
                .userToken(command.getUserToken())
                .user(user)
                .build();

        var bookmark = userStore.store(initBookmark);
        var board = boardReader.getBoard(bookmark.getBoardToken());
        return UserInfo.ImageInfo.of(bookmark, board);
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
        return feed.stream()
                .map(Feed::getBoardToken)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserInfo.ImageInfo addUserFeed(UserCommand.UserFeed command) {
        var user = userReader.findFirstByUserToken(command.getUserToken());
        var initFeed = Feed.builder()
                .boardToken(command.getBoardToken())
                .userToken(command.getUserToken())
                .user(user)
                .build();

        var feed = userStore.store(initFeed);
        var board = boardReader.getBoard(feed.getBoardToken());
        return UserInfo.ImageInfo.of(feed, board);
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
        var user = userReader.findFirstByUserToken(userToken);
        var userFeed = user.getFeedList()
                .stream()
                .map(feed -> {
                    var board = boardReader.getBoard(feed.getBoardToken());
                    return UserInfo.ImageInfo.of(feed, board);
                })
                .collect(Collectors.toList());
        var userBookmark = user.getBookmarkList()
                .stream()
                .map(bookmark -> {
                    var board = boardReader.getBoard(bookmark.getBoardToken());
                    return UserInfo.ImageInfo.of(bookmark, board);
                })
                .collect(Collectors.toList());

        return new UserInfo.UserProfile(user.getUserToken(), user.getNickname(),
                                        user.getProfilePictureUrl(),
                                        user.getMessage(), userFeed, userBookmark);
    }

    @Override
    @Transactional
    public UserInfo.ProfileInfo updateProfile(Profile command) {
        String userToken = getCurrentUserToken();
        var user = userReader.findFirstByUserToken(userToken);
        user.updateProfile(command);
        return userMapper.toProfileInfo(user);
    }
}
