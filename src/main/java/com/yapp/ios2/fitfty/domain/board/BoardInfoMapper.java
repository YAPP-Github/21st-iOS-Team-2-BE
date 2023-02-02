package com.yapp.ios2.fitfty.domain.board;

import com.yapp.ios2.fitfty.domain.tag.TagGroup;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface BoardInfoMapper {
        @Mappings({
                        @Mapping(source = "board.id", target = "boardId"),
                        @Mapping(expression = "java(board.getPicture().getFilePath())", target = "filePath")
        })
        BoardInfo.Main of(Board board);

        BoardInfo.TagGroupInfo of(TagGroup tagGroup);

        BoardInfo.PicturePathInfo toPicturePathInfo(Picture picture);

        // PictureInfo.Main toPictureInfo(List<PictureInfo.StyleInfo> styleInfoList);
        //
        // PictureInfo.StyleInfo of(String style, List<PictureInfo.PictureDetailInfo>
        // pictureInfoList);
        //
        // @Mappings({
        // @Mapping(source = "board.views", target = "views"),
        // @Mapping(expression = "java(board.getPicture().getFilePath())", target =
        // "filePath"),
        // @Mapping(source = "nickname", target = "nickname"),
        // @Mapping(source = "bookmarked", target = "bookmarked"),
        // })
        // PictureInfo.PictureDetailInfo of(Board board, String nickname, Boolean
        // bookmarked);
}
