package com.yapp.ios2.fitfty.infrastructure.tag;

import com.yapp.ios2.fitfty.domain.tag.TagGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagGroupRepository extends JpaRepository<TagGroup, Long> {
    Optional<TagGroup> findByTagValue(String tagValue);

//    @Query(nativeQuery=true, value="SELECT *  FROM question ORDER BY random() LIMIT 10")
    @Query(nativeQuery = true, value="SELECT * FROM yourTableName ORDER BY RAND() LIMIT 10 OFFSET floor(rand()(SELECT COUNT() FROM yourTableName))")
    List<TagGroup> findRandomPicture();

//    @Query("SELECT COUNT(t) FROM tag_group t")
//    int getNumberOfTagGroup();

//    @Query("SELECT u FROM tag_group u WHERE INDEX(u) IN :indexs")
//    List<TagGroup> getResultList(int[] indexes);

}
