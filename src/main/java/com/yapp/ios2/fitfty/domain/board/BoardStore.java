package com.yapp.ios2.fitfty.domain.board;

public interface BoardStore {
    Board store(Board board);
    Picture pictureStore(Picture picture);
    void deleteBoard(Board board);
}
