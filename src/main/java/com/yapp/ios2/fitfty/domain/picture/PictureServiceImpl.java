package com.yapp.ios2.fitfty.domain.picture;

import com.yapp.ios2.fitfty.domain.user.UserCommand.Bookmark;
import com.yapp.ios2.fitfty.domain.user.UserCommand.UserFeed;
import com.yapp.ios2.fitfty.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {
    private final BoardStore pictureStore;
    private final BoardReader boardReader;
    private final PictureSeriesFactory pictureSeriesFactory;
    private final BoardInfoMapper boardInfoMapper;
    private final UserService userService;

    @Override
    @Transactional
    public Board registerBoard(PictureCommand.RegisterBoardRequest request) {
        String userToken = userService.getCurrentUserToken();
        var picture = pictureSeriesFactory.store(request, userToken);
        var initBoard = request.toEntity(userToken, picture);
        var board = pictureStore.store(initBoard);
        userService.addUserFeed(UserFeed.builder()
                                        .userToken(userToken)
                                        .boardToken(board.getBoardToken())
                                        .build());

        return board;
    }

    @Override
    @Transactional
    public BoardInfo.Main changeBoardInfo(PictureCommand.RegisterBoardRequest request,
                                          String boardToken) {
        String userToken = userService.getCurrentUserToken();
        var board = boardReader.getBoard(boardToken);
        var picture = board.getPicture();
        picture.update(request);
        board.update(request, picture);

        return boardInfoMapper.of(board);
    }

    @Override
    @Transactional(readOnly = true)
    public BoardInfo.Main retrieveBoardInfo(String boardToken) {
        var board = boardReader.getBoard(boardToken);
        return boardInfoMapper.of(board);
    }

    @Override
    @Transactional
    public void deleteBoard(String boardToken) {
        String userToken = userService.getCurrentUserToken();
        var board = boardReader.getBoard(boardToken);
        userService.deleteUserFeed(UserFeed.builder()
                                           .userToken(userToken)
                                           .boardToken(boardToken)
                                           .build());
        board.deleteBoard();
    }

    @Override
    @Transactional
    public void addBookmark(String boardToken) {
        String userToken = userService.getCurrentUserToken();
        var board = boardReader.getBoard(boardToken);
        board.increaseBookmarkCnt();

        userService.addBookmark(Bookmark.builder()
                                        .boardToken(boardToken)
                                        .userToken(userToken)
                                        .build());
        // return 은 소미가 보고 원하는 형태로
    }

    @Override
    @Transactional
    public void deleteBookmark(String boardToken) {
        String userToken = userService.getCurrentUserToken();
        var board = boardReader.getBoard(boardToken);
        board.decreaseBookmarkCnt();

        userService.deleteBookmark(Bookmark.builder()
                                        .boardToken(boardToken)
                                        .userToken(userToken)
                                        .build());
    }
}
