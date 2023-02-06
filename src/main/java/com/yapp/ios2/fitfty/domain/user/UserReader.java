package com.yapp.ios2.fitfty.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserReader {

    Optional<User> findFirstByEmail(String email);

    User findFirstByUserToken(String userToken);

    Optional<User> findFirstByNickname(String nickname);

    List<Feed> findFeedByUserToken(String userToken);

    List<Bookmark> findBookmarkByUserToken(String userToken);
}
