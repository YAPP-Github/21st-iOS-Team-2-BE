package com.yapp.ios2.fitfty.domain.board;

import com.yapp.ios2.fitfty.interfaces.board.BoardDto;

public interface BoardService {
    Board registerBoard(BoardCommand.RegisterBoardRequest request);
    BoardInfo.Main retrieveBoardInfo(String boardToken);
    BoardInfo.Main changeBoardInfo(BoardCommand.RegisterBoardRequest request, String boardToken);
    PictureInfo.Main getPictureList(BoardDto.GetPictureRequest request);
    void deleteBoard(String boardToken);
    void addBookmark(String boardToken);
    void deleteBookmark(String boardToken);
}
