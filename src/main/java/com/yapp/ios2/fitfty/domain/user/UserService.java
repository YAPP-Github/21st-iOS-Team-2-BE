package com.yapp.ios2.fitfty.domain.user;

import java.util.List;

public interface UserService {

    String getCurrentUserToken();
    String registerUser(UserCommand.SignUp command);

    String findNickname(String nickname);

    UserInfo.CustomOption updateUserDetails(UserCommand.CustomOption command);

//    UserInfo.Profile getProfile(String userToken);
//    UserInfo.Profile updateProfile(UserCommand.Profile command);

//    UserInfo.Bookmark getBookmark(String userToken);
//    UserInfo.Bookmark addBookmark(String userToken, UserCommand.Bookmark bookmark);
//    void removeBookmakr(String userToken, UserCommand.Bookmark bookmark);

    List<String> getUserFeed(String userToken);
    UserInfo.UserFeed addUserFeed(UserCommand.UserFeed userFeed);
    void deleteUserFeed(UserCommand.UserFeed userFeed);

}


