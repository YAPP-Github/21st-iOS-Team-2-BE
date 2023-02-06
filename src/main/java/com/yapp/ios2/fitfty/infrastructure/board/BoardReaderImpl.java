package com.yapp.ios2.fitfty.infrastructure.board;

import com.yapp.ios2.fitfty.domain.board.Board;
import com.yapp.ios2.fitfty.domain.board.BoardReader;
import com.yapp.ios2.fitfty.domain.board.PictureInfo;
import com.yapp.ios2.fitfty.domain.tag.TagGroup;
import com.yapp.ios2.fitfty.domain.user.Bookmark;
import com.yapp.ios2.fitfty.domain.user.User;
import com.yapp.ios2.fitfty.domain.user.UserReader;
import com.yapp.ios2.fitfty.global.exception.EntityNotFoundException;
import com.yapp.ios2.fitfty.infrastructure.tag.TagGroupRepository;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
    public List<PictureInfo.StyleInfo> getPictureSeries(String userToken, String weather) {
        var bookmarkList = userReader.findBookmarkByUserToken(userToken)
                .stream()
                .map(Bookmark::getBoardToken)
                .collect(Collectors.toList());
        var user = userReader.findFirstByUserToken(userToken);

        var styleInfoList = user.getStyle()
                .stream()
                .map(style -> {
                    var tagGroupList = getRandomPicture(style, weather, user.getGender());
                    var pictureDetailInfoList = tagGroupList.stream()
                            .map(tagGroup -> {
                                var pictureId = tagGroup.getPictureId();
                                var board = boardRepository.findByPictureId(pictureId)
                                        .orElseThrow(EntityNotFoundException::new);
                                board.increaseViews();
                                Boolean bookmarked = bookmarkList.contains(board.getBoardToken());

                                return new PictureInfo.PictureDetailInfo(board, user.getNickname(),
                                                                         bookmarked);
                            })
                            .collect(Collectors.toList());

                    return new PictureInfo.StyleInfo(style, pictureDetailInfoList);
                })
                .collect(Collectors.toList());

        return styleInfoList;

    }

    @Override
    public List<TagGroup> getRandomPicture(String style, String weather, User.Gender gender) {
        String styleQuery = "%" + style + "%";
        GregorianCalendar today = new GregorianCalendar();
        long seed = today.get(today.YEAR) * 100000000L + today.get(today.MONTH) * 100 + today.get(
                today.DAY_OF_MONTH);
        int offset = (int) new Random(seed).nextFloat() * tagGroupRepository.getNumberOfTagGroup();

        List<TagGroup> tagGroupList = tagGroupRepository.findRandomPicture(styleQuery, weather,
                                                                           gender.toString(),
                                                                           seed, offset);

        return tagGroupList;
    }
}
