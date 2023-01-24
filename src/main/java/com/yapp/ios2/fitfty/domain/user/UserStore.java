package com.yapp.ios2.fitfty.domain.user;

public interface UserStore {

    User store(User user);

    Bookmark store(Bookmark bookmark);

    Feed store(Feed feed);

    void deleteFeedByUserTokenAndBoardToken(String userToken, String pictureToken);

    void deleteBookmarkByUserTokenAndBoardToken(String userToken, String pictureToken);
}
