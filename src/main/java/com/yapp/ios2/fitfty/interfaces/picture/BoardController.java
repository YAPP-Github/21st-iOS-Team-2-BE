package com.yapp.ios2.fitfty.interfaces.picture;

import com.yapp.ios2.fitfty.domain.picture.PictureService;
import com.yapp.ios2.fitfty.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.yapp.ios2.fitfty.global.util.Constants.API_PREFIX;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(API_PREFIX + "/boards")
public class BoardController {
    private final PictureService pictureService;
    private final PictureDtoMapper pictureDtoMapper;

    @PostMapping("/new")
    public CommonResponse registerBoard(@RequestBody @Valid BoardDto.RegisterBoardRequest request) {
        var pictureCommand = pictureDtoMapper.of(request);
        var board = pictureService.registerBoard(pictureCommand);
        var response = pictureDtoMapper.of(board);
        return CommonResponse.success(response);
    }

//    @PutMapping("/{boardToken}")
//    public CommonResponse changeBoardInfo(@PathVariable("boardToken") String boardToken, @RequestBody @Valid BoardDto.ChangeBoardInfoRequest request) {
//        var userToken = request.getUserToken();
//        pictureFacade.changeBoardInfo(boardToken);
//        return CommonResponse.success("OK");
//    }
//
    @DeleteMapping("/{boardToken}")
    public CommonResponse deleteBoard(@PathVariable("boardToken") String boardToken) {
        pictureService.deleteBoard(boardToken);
        return CommonResponse.success("OK");
    }

    @GetMapping("/{boardToken}")
    public CommonResponse retrieve(@PathVariable("boardToken") String boardToken) {
        var boardInfo = pictureService.retrieveBoardInfo(boardToken);
        var response = pictureDtoMapper.of(boardInfo);
        return CommonResponse.success(response);
    }
}
