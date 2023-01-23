package com.yapp.ios2.fitfty.interfaces.picture;

import static com.yapp.ios2.fitfty.global.util.Constants.API_PREFIX;

import com.yapp.ios2.fitfty.domain.picture.PictureService;
import com.yapp.ios2.fitfty.global.response.CommonResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(API_PREFIX + "/boards")
public class BoardController {
    private final PictureService pictureService;
    private final PictureDtoMapper pictureDtoMapper;

    @PostMapping("/new")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse registerBoard(@RequestBody @Valid BoardDto.RegisterBoardRequest request) {
        var pictureCommand = pictureDtoMapper.of(request);
        var board = pictureService.registerBoard(pictureCommand);
        var response = pictureDtoMapper.of(board);
        return CommonResponse.success(response);
    }

    @PutMapping("/{boardToken}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse changeBoardInfo(@PathVariable("boardToken") String boardToken, @RequestBody @Valid BoardDto.RegisterBoardRequest request) {
        var pictureCommand = pictureDtoMapper.of(request);
        var boardInfo = pictureService.changeBoardInfo(pictureCommand, boardToken);
        var response = pictureDtoMapper.of(boardInfo);
        return CommonResponse.success(response);
    }

    @GetMapping("/{boardToken}")
    public CommonResponse retrieve(@PathVariable("boardToken") String boardToken) {
        var boardInfo = pictureService.retrieveBoardInfo(boardToken);
        var response = pictureDtoMapper.of(boardInfo);
        return CommonResponse.success(response);
    }

    @DeleteMapping("/{boardToken}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse deleteBoard(@PathVariable("boardToken") String boardToken) {
        pictureService.deleteBoard(boardToken);
        return CommonResponse.success("OK");
    }

    @PostMapping("/bookmark/{boardToken}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse addBookmark(@PathVariable("boardToken") String boardToken) {
        pictureService.addBookmark(boardToken);
        return CommonResponse.success("OK");
    }

    @DeleteMapping("/bookmark/{boardToken}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse deleteBookmark(@PathVariable("boardToken") String boardToken) {
        pictureService.deleteBookmark(boardToken);
        return CommonResponse.success("OK");
    }

}
