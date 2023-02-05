package com.yapp.ios2.fitfty.interfaces.board;

import com.yapp.ios2.fitfty.domain.board.Board;
import com.yapp.ios2.fitfty.domain.board.BoardInfo;
import com.yapp.ios2.fitfty.domain.board.BoardCommand;
import com.yapp.ios2.fitfty.domain.board.PictureInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface BoardDtoMapper {
    // register
    @Mappings({@Mapping(source = "request.tagGroup", target = "registerTagGroupRequest"),
            @Mapping(source = "request.photoTakenTime", target = "photoTakenTime", dateFormat = "yyyy-MM-dd HH:mm:ss")})
    BoardCommand.RegisterBoardRequest of(BoardDto.RegisterBoardRequest request);

    BoardDto.RegisterResponse of(Board board);

    // retrieve
    BoardDto.Main of(BoardInfo.Main main);

    BoardDto.PictureListResponse of(PictureInfo.Main pictureInfo);

    BoardDto.StyleInfo of(PictureInfo.StyleInfo styleInfo);

    BoardDto.PictureDetailInfo of(PictureInfo.PictureDetailInfo pictureDetailInfo);

}
