package com.yapp.ios2.fitfty.domain.picture;

public interface PictureService {
    Board registerBoard(PictureCommand.RegisterBoardRequest request);
    BoardInfo.Main retrieveBoardInfo(String boardToken);
    BoardInfo.Main changeBoardInfo(PictureCommand.RegisterBoardRequest request, String boardToken);
    void deleteBoard(String boardToken);
    void addBookmark(String boardToken);
    void deleteBookmark(String boardToken);
}
