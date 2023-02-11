package com.yapp.ios2.fitfty.infrastructure.board;

import com.yapp.ios2.fitfty.domain.board.Board;
import com.yapp.ios2.fitfty.domain.board.BoardReader;
import com.yapp.ios2.fitfty.domain.board.PictureInfo;
import com.yapp.ios2.fitfty.domain.tag.TagGroup;
import com.yapp.ios2.fitfty.domain.user.Bookmark;
import com.yapp.ios2.fitfty.domain.user.UserReader;
import com.yapp.ios2.fitfty.global.exception.EntityNotFoundException;
import com.yapp.ios2.fitfty.global.exception.PictureNotFoundException;
import com.yapp.ios2.fitfty.global.response.ErrorCode;
import com.yapp.ios2.fitfty.infrastructure.tag.TagGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoardReaderImpl implements BoardReader {
    private final BoardRepository boardRepository;
    private final TagGroupRepository tagGroupRepository;
    private final UserReader userReader;

    @Override
    public Board getBoard(String boardToken) {
        return boardRepository.findByBoardToken(boardToken)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<PictureInfo.PictureDetailInfo> getPictureSeries(String userToken, String weather,
                                                                List<String> style, String gender) {
        var bookmarkList = userReader.findBookmarkByUserToken(userToken)
                .stream()
                .map(Bookmark::getBoardToken)
                .collect(Collectors.toList());
        var styleQuery = getStyleQuery(style);
        var pictureDetailInfoList = getPictureDetailInfoSeries(styleQuery, weather, gender,
                                                               bookmarkList);

        return pictureDetailInfoList;

    }

    public String getStyleQuery(List<String> style) {
        if (CollectionUtils.isEmpty(style)) {
            return "[A-Z]*";
        }

        Iterator<String> it = style.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(it.next());
        while (it.hasNext()) {
            sb.append("|" + it.next());
        }
        return sb.toString();
    }

    public List<PictureInfo.PictureDetailInfo> getPictureDetailInfoSeries(String style,
                                                                          String weather,
                                                                          String gender,
                                                                          List<String> bookmarkList) {
        var tagGroupList = getRandomPicture(style, weather, gender);
        var pictureDetailInfoList = tagGroupList.stream()
                .map(tagGroup -> {
                    var pictureId = tagGroup.getPictureId();
                    var board = boardRepository.findByPictureId(pictureId)
                            .orElseThrow(EntityNotFoundException::new);
                    var picture = board.getPicture();
                    var user = userReader.findFirstByUserToken(board.getUserToken());
                    board.increaseViews();
                    Boolean bookmarked = bookmarkList.contains(board.getBoardToken());

                    return new PictureInfo.PictureDetailInfo(picture.getFilePath(),
                                                             board.getBoardToken(),
                                                             board.getViews(),
                                                             user.getUserToken(),
                                                             user.getNickname(),
                                                             user.getProfilePictureUrl(),
                                                             bookmarked);
                })
                .collect(Collectors.toList());

        return pictureDetailInfoList;
    }

    @Override
    public List<TagGroup> getRandomPicture(String style, String weather, String gender) {
        GregorianCalendar today = new GregorianCalendar();
        long seed = today.get(today.YEAR) * 100000000L + today.get(today.MONTH) * 100 + today.get(
                today.DAY_OF_MONTH);
        int offset = (int) new Random(seed).nextFloat() * tagGroupRepository.getNumberOfTagGroup();

        List<TagGroup> tagGroupList = tagGroupRepository.findRandomPicture(weather,
                                                                           gender,
                                                                           style,
                                                                           seed, offset);
        if (CollectionUtils.isEmpty(tagGroupList)) {
            throw new PictureNotFoundException(ErrorCode.PICTURE_NOT_FOUND.getErrorMsg());
        }

        return tagGroupList;
    }
}
