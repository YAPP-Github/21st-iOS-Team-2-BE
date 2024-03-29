package com.yapp.ios2.fitfty.infrastructure.user;

import com.yapp.ios2.fitfty.domain.user.Feed;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    List<Feed> findByUserToken(String userToken);

    Optional<Feed> findFirstByUserTokenAndBoardToken(String userToken, String boardToken);
}
