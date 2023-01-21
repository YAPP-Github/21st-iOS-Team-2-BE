package com.yapp.ios2.fitfty.domain.user;

public interface UserStore {
    User store(User user);
    Bookmark store(Bookmark bookmark);

    Feed store(Feed feed);

    void deleteByUserTokenAndBoardToken(String userToken, String pictureToken);
}
