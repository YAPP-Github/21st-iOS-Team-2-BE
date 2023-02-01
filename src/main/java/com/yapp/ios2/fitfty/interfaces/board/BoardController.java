package com.yapp.ios2.fitfty.interfaces.board;

import static com.yapp.ios2.fitfty.global.util.Constants.API_PREFIX;

import com.yapp.ios2.fitfty.domain.board.BoardService;
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
    private final BoardService boardService;
    private final BoardDtoMapper boardDtoMapper;

    @PostMapping("/new")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse registerBoard(@RequestBody @Valid BoardDto.RegisterBoardRequest request) {
        var boardCommand = boardDtoMapper.of(request);
        var board = boardService.registerBoard(boardCommand);
        var response = boardDtoMapper.of(board);
        return CommonResponse.success(response);
    }

    @PutMapping("/{boardToken}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse changeBoardInfo(@PathVariable("boardToken") String boardToken,
                                          @RequestBody @Valid BoardDto.RegisterBoardRequest request) {
        var boardCommand = boardDtoMapper.of(request);
        var boardInfo = boardService.changeBoardInfo(boardCommand, boardToken);
        var response = boardDtoMapper.of(boardInfo);
        return CommonResponse.success(response);
    }

    @GetMapping("/{boardToken}")
    public CommonResponse retrieve(@PathVariable("boardToken") String boardToken) {
        var boardInfo = boardService.retrieveBoardInfo(boardToken);
        var response = boardDtoMapper.of(boardInfo);
        return CommonResponse.success(response);
    }

    @DeleteMapping("/{boardToken}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse deleteBoard(@PathVariable("boardToken") String boardToken) {
        boardService.deleteBoard(boardToken);
        return CommonResponse.success("OK");
    }

    @PostMapping("/bookmark/{boardToken}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse addBookmark(@PathVariable("boardToken") String boardToken) {
        boardService.addBookmark(boardToken);
        return CommonResponse.success("OK");
    }

    @DeleteMapping("/bookmark/{boardToken}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse deleteBookmark(@PathVariable("boardToken") String boardToken) {
        boardService.deleteBookmark(boardToken);
        return CommonResponse.success("OK");
    }

}
