package com.yapp.ios2.fitfty.domain.user;

import com.yapp.ios2.fitfty.domain.user.UserInfo.UserFeed;
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
//    UserInfo.Bookmark removeBookmakr(String userToken, UserCommand.Bookmark bookmark);

    List<UserInfo.UserFeed> getUserFeed(String userToken);
    UserInfo.UserFeed addUserFeed(UserCommand.UserFeed userFeed);
    UserInfo.UserFeed removeUserFeed(UserCommand.UserFeed userFeed);

}


