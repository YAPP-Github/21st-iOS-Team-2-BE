package com.yapp.ios2.fitfty.domain.board;

import com.yapp.ios2.fitfty.domain.user.UserMapper;
import com.yapp.ios2.fitfty.domain.user.UserReader;
import com.yapp.ios2.fitfty.domain.user.UserService;
import com.yapp.ios2.fitfty.interfaces.board.BoardDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        var board = boardReader.getBoard(boardToken);
        var userToken = board.getUserToken();
        var user = userReader.findFirstByUserToken(userToken);
        var tagGroupInfo = boardInfoMapper.of(board.getTagGroup());
        Boolean bookmarked = userService.getBookmark(userToken).contains(boardToken);
        board.increaseViews();
        return boardInfoMapper.of(board, user, tagGroupInfo, bookmarked);
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
        var pictureDetailInfoList = boardReader.getPictureSeries(userToken, request.getWeather(),
                                                                 user.getStyle(), user.getGender()
                                                                         .toString());
        return new PictureInfo.Main(pictureDetailInfoList);
    }

    @Override
    @Transactional
    public PictureInfo.Main getFilteredPictureList(BoardDto.GetFilteredPictureRequest request) {
        String userToken = userService.getCurrentUserToken();
        var pictureDetailInfoList = boardReader.getPictureSeries(userToken, request.getWeather(),
                                                                 request.getStyle(),
                                                                 request.getGender());
        return new PictureInfo.Main(pictureDetailInfoList);
    }
}
