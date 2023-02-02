package com.yapp.ios2.fitfty.infrastructure.tag;

import com.yapp.ios2.fitfty.domain.tag.TagGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagGroupRepository extends JpaRepository<TagGroup, Long> {
    Optional<TagGroup> findByTagValue(String tagValue);

    @Query(nativeQuery = true, value = "SELECT *" +
            "FROM tag_group" +
            "WHERE style = :style AND weather = :weather" +
            "ORDER BY RAND(:seed) LIMIT 5 OFFSET :offset")
    List<TagGroup> findRandomPicture(@Param("style") String style, @Param("weather") String weather,
            @Param("seed") long seed, @Param("offset") int offset);

    @Query("SELECT COUNT(t) FROM tag_group t")
    int getNumberOfTagGroup();

}
