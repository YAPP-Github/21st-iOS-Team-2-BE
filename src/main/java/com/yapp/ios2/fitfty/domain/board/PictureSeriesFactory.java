package com.yapp.ios2.fitfty.domain.board;

public interface PictureSeriesFactory {
    Picture store(BoardCommand.RegisterBoardRequest request, String userToken);
}
