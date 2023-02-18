package com.yapp.ios2.fitfty.domain.board;

import com.yapp.ios2.fitfty.domain.user.UserMapper;
import com.yapp.ios2.fitfty.domain.user.UserReader;
import com.yapp.ios2.fitfty.domain.user.UserService;
import com.yapp.ios2.fitfty.interfaces.board.BoardDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardStore boardStore;
    private final BoardReader boardReader;
    private final PictureSeriesFactory pictureSeriesFactory;
    private final BoardInfoMapper boardInfoMapper;
    private final UserService userService;
    private final UserReader userReader;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public Board registerBoard(BoardCommand.RegisterBoardRequest request) {
        String userToken = userService.getCurrentUserToken();
        var picture = pictureSeriesFactory.store(request, userToken);
        var initBoard = request.toEntity(userToken, picture);
        var board = boardStore.store(initBoard);
        userService.addUserFeed(userMapper.toUserFeedCommand(userToken, board.getBoardToken()));

        return board;
    }

    @Override
    @Transactional
    public void changeBoardInfo(BoardCommand.RegisterBoardRequest request,
                                String boardToken) {
        var board = boardReader.getBoard(boardToken);
        var picture = board.getPicture();
        var tagGroup = picture.getTagGroup();
        tagGroup.update(request);
        picture.update(request, tagGroup);
        board.update(request, picture);
    }

    @Override
    @Transactional
    public BoardInfo.Main retrieveBoardInfo(String boardToken) {
        String userToken = userService.checkNonMemeber();
        var bookmarkList = (userToken.equals(
                "NONMEMBER")) ? Collections.EMPTY_LIST : userService.getBookmark(userToken);
        var board = boardReader.getBoard(boardToken);
        var boardUser = userReader.findFirstByUserToken(board.getUserToken());
        var tagGroupInfo = boardInfoMapper.of(board.getTagGroup());
        Boolean bookmarked = bookmarkList.contains(boardToken);
        board.increaseViews();
        return boardInfoMapper.of(board, boardUser, tagGroupInfo, bookmarked);
    }

    @Override
    @Transactional
    public void deleteBoard(String boardToken) {
        String userToken = userService.getCurrentUserToken();
        var board = boardReader.getBoard(boardToken);
        boardStore.deleteBoard(board);
        userService.deleteUserFeed(userMapper.toUserFeedCommand(userToken, boardToken));
    }

    @Override
    @Transactional
    public void addBookmark(String boardToken) {
        String userToken = userService.getCurrentUserToken();
        var board = boardReader.getBoard(boardToken);
        board.increaseBookmarkCnt();

        userService.addBookmark(userMapper.toBookmarkCommand(userToken, boardToken));
    }

    @Override
    @Transactional
    public void deleteBookmark(String boardToken) {
        String userToken = userService.getCurrentUserToken();
        var board = boardReader.getBoard(boardToken);
        board.decreaseBookmarkCnt();

        userService.deleteBookmark(userMapper.toBookmarkCommand(userToken, boardToken));
    }

    @Override
    @Transactional
    public PictureInfo.Main getPictureList(BoardDto.GetPictureRequest request) {
        String userToken = userService.getCurrentUserToken();
        var user = userReader.findFirstByUserToken(userToken);
        var bookmarkList = userService.getBookmark(userToken);
        var pictureDetailInfoList = boardReader.getPictureSeries(userToken, bookmarkList,
                                                                 request.getWeather(),
                                                                 user.getStyle(), user.getGender()
                                                                         .toString());
        return new PictureInfo.Main(pictureDetailInfoList);
    }

    @Override
    @Transactional
    public PictureInfo.Main getFilteredPictureList(BoardDto.GetFilteredPictureRequest request) {
        String userToken = userService.checkNonMemeber();
        var bookmarkList = (userToken.equals(
                "NONMEMBER")) ? Collections.EMPTY_LIST : userService.getBookmark(userToken);
        var pictureDetailInfoList = boardReader.getPictureSeries(userToken, bookmarkList,
                                                                 request.getWeather(),
                                                                 request.getStyle(),
                                                                 request.getGender());
        return new PictureInfo.Main(pictureDetailInfoList);
    }
}
