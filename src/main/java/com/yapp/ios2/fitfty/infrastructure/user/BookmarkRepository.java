package com.yapp.ios2.fitfty.infrastructure.user;

import com.yapp.ios2.fitfty.domain.user.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

}
