package com.yapp.ios2.fitfty.domain.user;

public interface UserStore {
    User store(User user);
    Bookmark store(Bookmark bookmark);

    Feed store(Feed feed);

    void deleteByUserTokenAndPictureToken(String userToken, String pictureToken);
}
