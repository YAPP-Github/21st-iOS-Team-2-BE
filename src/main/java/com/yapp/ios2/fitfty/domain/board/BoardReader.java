package com.yapp.ios2.fitfty.domain.board;

import com.yapp.ios2.fitfty.domain.tag.TagGroup;
import com.yapp.ios2.fitfty.interfaces.board.BoardDto;

import java.util.List;

public interface BoardReader {
    Board getBoard(String boardToken);
    List<PictureInfo.StyleInfo> getPictureSeries(String userToken, String weather);
    List<TagGroup> getRandomPicture(String style, String weather);
}
