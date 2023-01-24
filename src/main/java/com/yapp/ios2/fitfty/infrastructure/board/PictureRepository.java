package com.yapp.ios2.fitfty.infrastructure.board;

import com.yapp.ios2.fitfty.domain.board.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    Optional<Picture> findByPictureToken(String pictureToken);
}
