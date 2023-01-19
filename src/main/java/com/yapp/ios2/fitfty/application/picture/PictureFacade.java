package com.yapp.ios2.fitfty.application.picture;

import com.yapp.ios2.fitfty.domain.picture.Board;
import com.yapp.ios2.fitfty.domain.picture.PictureCommand;
import com.yapp.ios2.fitfty.domain.picture.PictureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PictureFacade {
    private final PictureService pictureService;

    public Board registerBoard(PictureCommand.RegisterBoardRequest request) {
        var board = pictureService.registerBoard(request);
        return board;
    }

}
