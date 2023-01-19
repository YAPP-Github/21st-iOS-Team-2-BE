package com.yapp.ios2.fitfty.infrastructure.picture;

import com.yapp.ios2.fitfty.domain.picture.Picture;
import com.yapp.ios2.fitfty.domain.picture.PictureCommand;
import com.yapp.ios2.fitfty.domain.picture.PictureSeriesFactory;
import com.yapp.ios2.fitfty.domain.picture.BoardStore;
import com.yapp.ios2.fitfty.domain.tag.TagGroupStore;
import com.yapp.ios2.fitfty.domain.tag.TagStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class PictureSeriesFactoryImpl implements PictureSeriesFactory {
    private final BoardStore boardStore;
    private final TagGroupStore tagGroupStore;
    private final TagStore tagStore;

    @Override
    public Picture store(PictureCommand.RegisterBoardRequest request) {
        Long userId = 1234L;
        var picture = boardStore.pictureStore(request.toPictureEntity(userId));
        var registerTagGroupRequestList = request.getRegisterTagGroupRequestList();
        if (CollectionUtils.isEmpty(registerTagGroupRequestList)) {
            return picture;
        }

        registerTagGroupRequestList.stream()
                .map(requestTagGroup -> {
                    // tagGroup store
                    var initTagGroup = requestTagGroup.toEntity(picture);
                    var tagGroup = tagGroupStore.store(initTagGroup);

                    // tag store
                    requestTagGroup.getRegisterTagRequestList()
                            .forEach(requestTag -> {
                                var initTag = requestTag.toEntity(tagGroup);
                                tagStore.store(initTag);
                            });
                    return picture;
                });
        return picture;
    }
}
