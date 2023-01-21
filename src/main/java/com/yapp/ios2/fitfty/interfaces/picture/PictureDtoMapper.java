package com.yapp.ios2.fitfty.interfaces.picture;

import com.yapp.ios2.fitfty.domain.picture.Board;
import com.yapp.ios2.fitfty.domain.picture.BoardInfo;
import com.yapp.ios2.fitfty.domain.picture.PictureCommand;
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
public interface PictureDtoMapper {
    // register
    @Mappings({@Mapping(source = "request.tagGroupList", target = "registerTagGroupRequestList"),
            @Mapping(source = "request.photoTakenTime", target = "photoTakenTime", dateFormat = "yyyy-MM-dd HH:mm:ss")})
    PictureCommand.RegisterBoardRequest of(BoardDto.RegisterBoardRequest request);

    PictureCommand.RegisterTagGroupRequest of(BoardDto.RegisterTagGroupRequest request);

    @Mappings({@Mapping(source = "board.picture", target = "picture")})
    BoardDto.RegisterResponse of(Board board);

    // retrieve
    BoardDto.Main of(BoardInfo.Main main);
}
