package com.yapp.ios2.fitfty.domain.user;

import java.util.List;

public interface UserService {

    String getCurrentUserToken();
    String registerUser(UserCommand.SignUp command);

    String findNickname(String nickname);

    UserInfo.CustomOption updateUserDetails(UserCommand.CustomOption command);

//    UserInfo.Profile getProfile(String userToken);
//    UserInfo.Profile updateProfile(UserCommand.Profile command);

    List<String> getBookmark(String userToken);
    UserInfo.Bookmark addBookmark(UserCommand.Bookmark bookmark);
    void deleteBookmark(UserCommand.Bookmark bookmark);

    List<String> getUserFeed(String userToken);
    UserInfo.UserFeed addUserFeed(UserCommand.UserFeed userFeed);
    void deleteUserFeed(UserCommand.UserFeed userFeed);

}


