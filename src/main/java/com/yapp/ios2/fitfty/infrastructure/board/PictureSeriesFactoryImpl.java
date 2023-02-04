package com.yapp.ios2.fitfty.infrastructure.board;

import com.yapp.ios2.fitfty.domain.board.BoardCommand;
import com.yapp.ios2.fitfty.domain.board.BoardStore;
import com.yapp.ios2.fitfty.domain.board.Picture;
import com.yapp.ios2.fitfty.domain.board.PictureSeriesFactory;
import com.yapp.ios2.fitfty.domain.tag.TagGroupStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PictureSeriesFactoryImpl implements PictureSeriesFactory {
    private final BoardStore boardStore;
    private final TagGroupStore tagGroupStore;

    @Override
    public Picture store(BoardCommand.RegisterBoardRequest request, String userToken) {
        var picture = boardStore.pictureStore(request.toPictureEntity(userToken));
        var registerTagGroupRequest = request.getRegisterTagGroupRequest();
        var initTagGroup = registerTagGroupRequest.toEntity(picture);
        tagGroupStore.store(initTagGroup);
        return picture;
    }

}
