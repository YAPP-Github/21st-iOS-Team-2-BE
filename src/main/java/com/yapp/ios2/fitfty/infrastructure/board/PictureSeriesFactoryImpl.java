package com.yapp.ios2.fitfty.infrastructure.board;

import com.yapp.ios2.fitfty.domain.board.BoardCommand;
import com.yapp.ios2.fitfty.domain.board.BoardStore;
import com.yapp.ios2.fitfty.domain.board.Picture;
import com.yapp.ios2.fitfty.domain.board.PictureSeriesFactory;
import com.yapp.ios2.fitfty.domain.tag.TagGroupStore;
import com.yapp.ios2.fitfty.domain.tag.TagStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

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
        System.out.println(picture);

        var initTagGroup = registerTagGroupRequest.toEntity(picture);
        var tagGroup = tagGroupStore.store(initTagGroup);
//        storeTagSeries(request, picture);
        return picture;
    }

    @Override
    public Picture storeTagSeries(BoardCommand.RegisterBoardRequest request, Picture picture) {
        var registerTagGroupRequest = request.getRegisterTagGroupRequest();

        var initTagGroup = registerTagGroupRequest.toEntity(picture);
        var tagGroup = tagGroupStore.store(initTagGroup);

        return picture;

//        registerTagGroupRequest.stream()
//                .map(requestTagGroup -> {
//                    // tagGroup store
//                    var initTagGroup = requestTagGroup.toEntity(picture);
//                    var tagGroup = tagGroupStore.store(initTagGroup);
//
//                    return initTagGroup;
//                })
//                .collect(Collectors.toList());
    }
}
