package com.yapp.ios2.fitfty.domain.board;

import com.yapp.ios2.fitfty.interfaces.board.BoardDto;

import java.util.List;

public interface BoardReader {
    Board getBoard(String boardToken);
    List<PictureInfo.StyleInfo> getPictureSeries(String userToken, String weather);
    List<PictureInfo.PictureDetailInfo> getRandomPicture(String tagValue);
}
