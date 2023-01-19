package com.yapp.ios2.fitfty.domain.picture;

import com.yapp.ios2.fitfty.domain.tag.Tag;
import com.yapp.ios2.fitfty.domain.tag.TagGroup;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface BoardInfoMapper {
    @Mappings({
            @Mapping(source = "board.id", target = "boardId"),
            @Mapping(source = "board.picture", target = "picture"),
    })
    BoardInfo.Main of(Board board);

    BoardInfo.PictureInfo of(Picture picture);

    BoardInfo.TagGroupInfo of(TagGroup tagGroup);

    BoardInfo.TagInfo of(Tag tag);
}
