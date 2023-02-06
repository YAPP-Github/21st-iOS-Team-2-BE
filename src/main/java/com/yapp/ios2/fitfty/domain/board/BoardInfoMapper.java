package com.yapp.ios2.fitfty.domain.board;

import com.yapp.ios2.fitfty.domain.tag.TagGroup;
import com.yapp.ios2.fitfty.domain.user.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface BoardInfoMapper {
    @Mappings({
            @Mapping(source = "board.id", target = "boardId"),
            @Mapping(expression = "java(board.getPicture().getFilePath())", target = "filePath"),
            @Mapping(expression = "java(user.getNickname())", target = "nickname")
    })
    BoardInfo.Main of(Board board, User user);

    BoardInfo.TagGroupInfo of(TagGroup tagGroup);

    BoardInfo.PicturePathInfo toPicturePathInfo(Picture picture);
}
