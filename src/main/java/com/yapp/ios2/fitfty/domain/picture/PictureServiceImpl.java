package com.yapp.ios2.fitfty.domain.picture;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService{
    private final BoardStore pictureStore;
    private final BoardReader boardReader;
    private final PictureSeriesFactory pictureSeriesFactory;
    private final BoardInfoMapper boardInfoMapper;

    @Override
    @Transactional
    public Board registerBoard(PictureCommand.RegisterBoardRequest request) {
        // user 알아내기 임시로 저장
        Long userToken = 1234L;

        //1. picture 객체 생성 및 저장
        var picture = pictureSeriesFactory.store(request);

        //2. 보드 저장
        var initBoard = request.toEntity(userToken, picture);
        var board = pictureStore.store(initBoard);

        return board;
    }

    @Override
    @Transactional
    public BoardInfo.Main retrieveBoardInfo(String boardToken) {
        return null;
    }
}
