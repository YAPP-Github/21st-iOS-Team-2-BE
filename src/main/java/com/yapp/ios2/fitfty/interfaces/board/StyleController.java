package com.yapp.ios2.fitfty.interfaces.board;

import com.yapp.ios2.fitfty.domain.board.BoardService;
import com.yapp.ios2.fitfty.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.yapp.ios2.fitfty.global.util.Constants.API_PREFIX;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(API_PREFIX + "/styles")
public class StyleController {
    private final BoardService boardService;
    private final BoardDtoMapper boardDtoMapper;

    @GetMapping()
    public CommonResponse getPictureList(@RequestBody @Valid BoardDto.GetPictureRequest request) {
        var board = boardService.getPictureList(request);
        var response = boardDtoMapper.of(board);
        return CommonResponse.success(response);
    }
}
