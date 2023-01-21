package com.yapp.ios2.fitfty.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserReader {
    Optional<User> findOneByEmail(String email);
    Optional<User> findOneByUserToken(String userToken);

    Optional<User> findOneByNickname(String nickname);

    List<Feed> findByUserToken(String userToken);
}
