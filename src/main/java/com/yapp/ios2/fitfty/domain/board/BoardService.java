package com.yapp.ios2.fitfty.domain.board;

import com.yapp.ios2.fitfty.interfaces.board.BoardDto;

public interface BoardService {
    Board registerBoard(BoardCommand.RegisterBoardRequest request);
    BoardInfo.Main retrieveBoardInfo(String boardToken);
    PictureInfo.Main getPictureList(BoardDto.GetPictureRequest request);
    void changeBoardInfo(BoardCommand.RegisterBoardRequest request, String boardToken);
    void deleteBoard(String boardToken);
    void addBookmark(String boardToken);
    void deleteBookmark(String boardToken);
}
