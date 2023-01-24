package com.yapp.ios2.fitfty.domain.board;

public interface BoardService {
    Board registerBoard(BoardCommand.RegisterBoardRequest request);
    BoardInfo.Main retrieveBoardInfo(String boardToken);
    BoardInfo.Main changeBoardInfo(BoardCommand.RegisterBoardRequest request, String boardToken);
    void deleteBoard(String boardToken);
    void addBookmark(String boardToken);
    void deleteBookmark(String boardToken);
}
