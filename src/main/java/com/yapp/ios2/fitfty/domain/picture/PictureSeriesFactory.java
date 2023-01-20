package com.yapp.ios2.fitfty.domain.picture;

public interface PictureSeriesFactory {
    Picture store(PictureCommand.RegisterBoardRequest request, String userToken);
}
