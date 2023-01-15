package com.yapp.ios2.fitfty.domain.user;

public interface UserService {

    String registerUser(UserCommand.SignUp command);

    String findNickname(String nickname);

//    UserInfo.Profile getProfile(String userToken);
//    UserInfo.Profile updateProfile(UserCommand.Profile command);

//    UserInfo.Bookmark getBookmark(String userToken);
//    UserInfo.Bookmark addBookmark(String userToken, UserCommand.Bookmark bookmark);
//    UserInfo.Bookmark removeBookmakr(String userToken, UserCommand.Bookmark bookmark);
}


