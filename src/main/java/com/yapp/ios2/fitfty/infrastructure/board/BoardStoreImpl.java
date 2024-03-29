package com.yapp.ios2.fitfty.infrastructure.board;

import com.yapp.ios2.fitfty.domain.board.Board;
import com.yapp.ios2.fitfty.domain.board.BoardStore;
import com.yapp.ios2.fitfty.domain.board.Picture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoardStoreImpl implements BoardStore {
    private final BoardRepository boardRepository;
    private final PictureRepository pictureRepository;

    @Override
    public Board store(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public Picture pictureStore(Picture picture) {
        return pictureRepository.save(picture);
    }

    @Override
    public void deleteBoard(Board board) {
        boardRepository.delete(board);
    }
}
