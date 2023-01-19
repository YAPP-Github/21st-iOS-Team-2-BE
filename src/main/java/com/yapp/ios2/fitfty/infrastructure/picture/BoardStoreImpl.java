package com.yapp.ios2.fitfty.infrastructure.picture;

import com.yapp.ios2.fitfty.domain.picture.Board;
import com.yapp.ios2.fitfty.domain.picture.BoardStore;
import com.yapp.ios2.fitfty.domain.picture.Picture;
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

//    private void validCheck(Item item) {
//        if (StringUtils.isEmpty(item.getItemToken())) throw new InvalidParamException("Item.itemToken");
//        if (StringUtils.isEmpty(item.getItemName())) throw new InvalidParamException("Item.itemName");
//        if (item.getPartnerId() == null) throw new InvalidParamException("Item.partnerId");
//        if (item.getItemPrice() == null) throw new InvalidParamException("Item.itemPrice");
//        if (item.getStatus() == null) throw new InvalidParamException("Item.status");
//    }
}
