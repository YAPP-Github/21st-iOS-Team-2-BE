package com.yapp.ios2.fitfty.domain.picture;

public interface PictureService {
    Board registerBoard(PictureCommand.RegisterBoardRequest request);

    void deleteBoard(String boardToken);

    //    void changeTag(PictureCommand.ChangeTagRequest request, String userToken);
//    void changeBoardInfo(PictureCommand.ChangeBoardInfo request, String userToken);
    BoardInfo.Main retrieveBoardInfo(String boardToken);
}
