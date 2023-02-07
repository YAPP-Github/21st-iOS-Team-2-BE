package com.yapp.ios2.fitfty.infrastructure.tag;

import com.yapp.ios2.fitfty.domain.tag.TagGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagGroupRepository extends JpaRepository<TagGroup, Long> {

    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM tag_group " +
            "WHERE weather = :weather AND gender = :gender AND style REGEXP :style" +
            " ORDER BY RAND(:seed) LIMIT 15 OFFSET :offset")
    List<TagGroup> findRandomPicture(@Param("weather") String weather,
                                     @Param("gender") String gender,
                                     @Param("style") String style,
                                     @Param("seed") long seed, @Param("offset") int offset);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM tag_group")
    int getNumberOfTagGroup();

}
