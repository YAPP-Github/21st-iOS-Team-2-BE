package com.yapp.ios2.fitfty.domain.picture;

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

        return board;
    }

    @Override
    @Transactional
    public void deleteBoard(String boardToken) {
        var board = boardReader.getBoard(boardToken);
        board.deleteBoard();
    }

    @Override
    @Transactional(readOnly = true)
    public BoardInfo.Main retrieveBoardInfo(String boardToken) {
        var board = boardReader.getBoard(boardToken);
        return boardInfoMapper.of(board);
    }
}
