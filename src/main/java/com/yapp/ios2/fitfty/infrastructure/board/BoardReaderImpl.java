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
    private final TagGroupRepository tagGroupRepository;
    private final UserReader userReader;

    @Override
    public Board getBoard(String boardToken) {
        return boardRepository.findByBoardToken(boardToken)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<PictureInfo.StyleInfo> getPictureSeries(String userToken, String weather) {
//        style, weather tag로 s3 link 5개씩 찾아오기 (repository)
//                4. picture dto로부터 board info 찾기
//        1. user code, view, bookmark 여부 return
//                2. bookmark list에 있는지 contains() 메서드로 확인하기
//        3. 조회수 + 1

//        userReader.findOneByNickname(nickname)
//                .isPresent();

        var bookmarkList = userReader.findBookmarkByUserToken(userToken);
        var user = userReader.findOneByUserToken(userToken);
//        var pictureList = user.getStyle().stream()
//                .map(style -> {
//                    var picutreDetailList = getRandomPicture(style);
//
//                     PictureInfo.PictureDetailInfo.builder()
//                            .filePath()
//                            .nickname(user.getNickname())
//                            .views()
//                            .bookmarked()
//                            .build();
//
//                    return PictureInfo.StyleInfo.builder()
//                            .style(style)
//                            .pictureInfoList()
//                            .build();
//                }).collect(Collectors.toList());
//
//        var itemOptionGroupList = item.getItemOptionGroupList();
//        return itemOptionGroupList.stream()
//                .map(itemOptionGroup -> {
//                    var itemOptionList = itemOptionGroup.getItemOptionList();
//                    var itemOptionInfoList = itemOptionList.stream()
//                            .map(ItemInfo.ItemOptionInfo::new)
//                            .collect(Collectors.toList());
//
//                    return new ItemInfo.ItemOptionGroupInfo(itemOptionGroup, itemOptionInfoList);
//                }).collect(Collectors.toList());


        return null;

    }

    @Override
    public List<PictureInfo.PictureDetailInfo> getRandomPicture(String tagValue) {
//        var tagGroup = tagGroupRepository.findByTagValue(tagValue);

//        var size = tagGroupRepository.getNumberOfTagGroup();
//        var randomList = generateNumbers(System.currentTimeMillis(), size); // 날짜 기준으로 변경
//
//        int[] index = new Random().ints(0, size, 10).toArray();

        // 날짜 기준으로 변경
        List<TagGroup> tagGroupList = tagGroupRepository.findRandomPicture();

//        Query query = em.createQuery("SELECT u FROM UserTable u WHERE INDEX(u) IN :indexs");
//        query.setParameter("indexs", index);
//        List<UserTable> listUsersRandom = query.getResultList();
        return null;
    }

    public double[] generateNumbers(long seed, int amount) {
        double[] randomList = new double[amount];
        for (int i=0;i<amount;i++) {
            Random generator = new Random(seed);
            randomList[i] = Math.abs((double) (generator.nextLong() % 0.001) * 10000);
            seed--;
        }
        return randomList;
    }
}
