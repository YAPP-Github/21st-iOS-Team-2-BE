package com.yapp.ios2.fitfty.infrastructure.user;

import com.yapp.ios2.fitfty.domain.user.Bookmark;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByUserToken(String userToken);

    Optional<Bookmark> findFirstByUserTokenAndBoardToken(String userToken, String boardToken);

    List<Bookmark> findAllByBoardToken(String boardToken);
}
