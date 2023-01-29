package com.yapp.ios2.fitfty.domain.board;

import com.yapp.ios2.fitfty.domain.user.UserMapper;
import com.yapp.ios2.fitfty.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardStore pictureStore;
    private final BoardReader boardReader;
    private final PictureSeriesFactory pictureSeriesFactory;
    private final BoardInfoMapper boardInfoMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public Board registerBoard(BoardCommand.RegisterBoardRequest request) {
        String userToken = userService.getCurrentUserToken();
        var picture = pictureSeriesFactory.store(request, userToken);
        var initBoard = request.toEntity(userToken, picture);
        var board = pictureStore.store(initBoard);
        userService.addUserFeed(userMapper.toUserFeedCommand(userToken, board.getBoardToken()));

        return board;
    }

    @Override
    @Transactional
    public BoardInfo.Main changeBoardInfo(BoardCommand.RegisterBoardRequest request,
                                          String boardToken) {
        var board = boardReader.getBoard(boardToken);
        var picture = board.getPicture();
        picture.update(request);
        board.update(request, picture);

        return boardInfoMapper.of(board);
    }

    @Override
    @Transactional
    public BoardInfo.Main retrieveBoardInfo(String boardToken) {
        var board = boardReader.getBoard(boardToken);
        board.increaseViews();
        return boardInfoMapper.of(board);
    }

    @Override
    @Transactional
    public void deleteBoard(String boardToken) {
        String userToken = userService.getCurrentUserToken();
        var board = boardReader.getBoard(boardToken);
        userService.deleteUserFeed(userMapper.toUserFeedCommand(userToken, boardToken));
        board.deleteBoard();
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
}
