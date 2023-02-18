package com.yapp.ios2.fitfty.domain.board;

import com.yapp.ios2.fitfty.domain.tag.TagGroup;

import java.util.List;

public interface BoardReader {
    Board getBoard(String boardToken);

    List<PictureInfo.PictureDetailInfo> getPictureSeries(String currentUser,
                                                         List<String> bookmarkList, String weather,
                                                         List<String> style, String gender);

    List<TagGroup> getRandomPicture(String style, String weather, String gender);
}
