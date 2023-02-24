package com.yapp.ios2.fitfty.domain.user;

import java.util.List;

public interface UserService {

    String checkNonMemeber();

    String getCurrentUserToken();

    User registerUser(UserCommand.SignUp command);

    Boolean findNickname(String nickname);

    String findUserToken(String nickname);

    UserInfo.CustomOption getUserDetail();
    UserInfo.CustomOption updateUserDetails(UserCommand.CustomOption command);
    UserInfo.CustomPrivacy getUserPrivacy();
    UserInfo.CustomPrivacy updateUserPrivacy(UserCommand.CustomPrivacy command);

    List<String> getBookmark(String userToken);

    UserInfo.ImageInfo addBookmark(UserCommand.Bookmark bookmark);

    void deleteBookmark(UserCommand.Bookmark bookmark);
    void deleteAllExistingBookmark(String boardToken);

    List<String> getUserFeed(String userToken);

    UserInfo.ImageInfo addUserFeed(UserCommand.UserFeed userFeed);

    void deleteUserFeed(UserCommand.UserFeed userFeed);

    UserInfo.UserProfile retrieveProfile(String nickname);

    UserInfo.ProfileInfo updateProfile(UserCommand.Profile command);
}


