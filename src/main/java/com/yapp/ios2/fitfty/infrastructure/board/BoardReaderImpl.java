package com.yapp.ios2.fitfty.infrastructure.board;

import com.yapp.ios2.fitfty.domain.board.Board;
import com.yapp.ios2.fitfty.domain.board.BoardReader;
import com.yapp.ios2.fitfty.domain.board.PictureInfo;
import com.yapp.ios2.fitfty.domain.tag.TagGroup;
import com.yapp.ios2.fitfty.domain.user.UserReader;
import com.yapp.ios2.fitfty.global.exception.EntityNotFoundException;
import com.yapp.ios2.fitfty.infrastructure.tag.TagGroupRepository;
import com.yapp.ios2.fitfty.infrastructure.user.UserRepository;
import com.yapp.ios2.fitfty.interfaces.board.BoardDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoardReaderImpl implements BoardReader {
    private final BoardRepository boardRepository;
    private final PictureRepository pictureRepository;
    private final TagGroupRepository tagGroupRepository;
    private final UserReader userReader;

    @Override
    public Board getBoard(String boardToken) {
        return boardRepository.findByBoardToken(boardToken)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<PictureInfo.StyleInfo> getPictureSeries(String userToken, String weather) {
        var bookmarkList = userReader.findBookmarkByUserToken(userToken).stream()
                .map(Bookmark::getBoardToken)
                .collect(Collectors.toList());
        var user = userReader.findOneByUserToken(userToken);

        var styleInfoList = user.getStyle().stream()
                .map(style -> {
                    // 날짜 기준으로 변경, offset query 변경
                    var tagGroupList = getRandomPicture(style, weather);
                    var PictureDetailInfoList = tagGroupList.stream()
                            .map(tagGroup -> {
                                var picture = pictureRepository.findById(tagGroup.getPictureId());
                                var board = boardRepository.findByBoardToken(picture.getBoardToken());
                                board.increaseViews();
                                Boolean bookmarked = bookmarkList.contains(board.getBoardToken());

                                return new PictureInfo.PictureDetailInfo(board, user.getNickname, bookmarked);
                            }).collect(Collectors.toList());

                    return new PictureInfo.StyleInfo(style, PictureDetailInfo);
                }).collect(Collectors.toList());

        return styleInfoList;

    }

    @Override
    public List<PictureInfo.PictureDetailInfo> getRandomPicture(String style, String weather) {
        GregorianCalendar today = new GregorianCalendar();
        long seed = today.get(today.YEAR) * 10000 + today.get(today.MONTH) * 100 + today.get(today.DAY_OF_MONTH);
        int offset = new Random(seed).nextFloat() * tagGroupRepository.getNumberOfTagGroup();

        List<TagGroup> tagGroupList = tagGroupRepository.findRandomPicture(style, weather, seed, offset);

        return tagGroupList;
    }

    public double[] generateNumbers(long seed, int amount) {
        double[] randomList = new double[amount];
        for (int i = 0; i < amount; i++) {
            Random generator = new Random(seed);
            randomList[i] = Math.abs((double) (generator.nextLong() % 0.001) * 10000);
            seed--;
        }
        return randomList;
    }
}
