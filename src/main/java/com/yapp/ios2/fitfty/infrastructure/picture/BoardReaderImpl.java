package com.yapp.ios2.fitfty.infrastructure.picture;

import com.yapp.ios2.fitfty.domain.picture.Board;
import com.yapp.ios2.fitfty.domain.picture.BoardReader;
import com.yapp.ios2.fitfty.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoardReaderImpl implements BoardReader {
    private final BoardRepository boardRepository;

    @Override
    public Board getBoard(String boardToken) {
        return boardRepository.findByBoardToken(boardToken)
                .orElseThrow(EntityNotFoundException::new);
    }
}
