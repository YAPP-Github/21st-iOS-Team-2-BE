package com.yapp.ios2.fitfty.infrastructure.tag;

import com.yapp.ios2.fitfty.domain.tag.TagGroup;
import com.yapp.ios2.fitfty.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagGroupRepository extends JpaRepository<TagGroup, Long> {
    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM tag_group " +
            "WHERE style LIKE :style AND weather = :weather AND gender = :gender " +
            "ORDER BY RAND(:seed) LIMIT 5 OFFSET :offset")
    List<TagGroup> findRandomPicture(@Param("style") String style, @Param("weather") String weather,
                                     @Param("gender") String gender,
                                     @Param("seed") long seed, @Param("offset") int offset);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM tag_group")
    int getNumberOfTagGroup();

}
