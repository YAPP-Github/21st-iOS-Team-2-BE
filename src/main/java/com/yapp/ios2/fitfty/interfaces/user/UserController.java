package com.yapp.ios2.fitfty.interfaces.user;

import static com.yapp.ios2.fitfty.global.util.Constants.API_PREFIX;

import com.yapp.ios2.fitfty.domain.user.UserCommand;
import com.yapp.ios2.fitfty.domain.user.UserService;
import com.yapp.ios2.fitfty.global.response.CommonResponse;
import com.yapp.ios2.fitfty.interfaces.user.UserDto.CustomOption;
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
@RequestMapping(API_PREFIX + "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    @GetMapping("/nickname/{nickname}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse findNickname(@PathVariable String nickname) {
        return CommonResponse.success(userService.findNickname(nickname));
    }

    @GetMapping("/details")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse getUserDetails() {
        return CommonResponse.success(userService.getUserDetail());
    }

    @PutMapping("/details")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse updateUserDetails(@RequestBody CustomOption request) {
        var userCommand = userDtoMapper.of(request);
        var response = userService.updateUserDetails(userCommand);
        return CommonResponse.success(response);
    }

    @GetMapping("/privacy")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse getUserPrivacy() {
        return CommonResponse.success(userService.getUserPrivacy());
    }

    @PutMapping("/privacy")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse updateUserPrivacy(@RequestBody UserDto.CustomPrivacy request) {
        var userCommand = userDtoMapper.of(request);
        var response = userService.updateUserPrivacy(userCommand);
        return CommonResponse.success(response);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse getMyProfile() {
        return CommonResponse.success(userService.retrieveProfile(null));
    }

    @GetMapping("/profile/{nickname}")
    public CommonResponse getProfile(@PathVariable String nickname) {
        return CommonResponse.success(userService.retrieveProfile(nickname));
    }

    @PutMapping("/profile")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse updateMyProfile(@RequestBody UserCommand.Profile command) {
        return CommonResponse.success(userService.updateProfile(command));
    }

    @PutMapping("/style")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse updateMyStyle() {
        return CommonResponse.success("/users/style");
    }

    // 내부 메서드 테스트용
    @GetMapping("/feed/{userToken}")
    public CommonResponse getFeed(@PathVariable String userToken) {

        log.info("/feed/{userToken}" + userToken);
        return CommonResponse.success(userService.getUserFeed(userToken));
    }

    @PostMapping("/feed")
    public CommonResponse addFeed(@RequestBody UserCommand.UserFeed command) {
        return CommonResponse.success(userService.addUserFeed(command));
    }

    @DeleteMapping("/feed")
    public CommonResponse deleteFeed(@RequestBody UserCommand.UserFeed command) {
        userService.deleteUserFeed(command);
        return CommonResponse.success("OK");
    }

    @GetMapping("/bookmark/{userToken}")
    public CommonResponse getBookmark(@PathVariable String userToken) {

        log.info("/bookmark/{userToken}" + userToken);
        return CommonResponse.success(userService.getBookmark(userToken));
    }

    @PostMapping("/bookmark")
    public CommonResponse addBookmark(@RequestBody UserCommand.Bookmark command) {
        return CommonResponse.success(userService.addBookmark(command));
    }

    @DeleteMapping("/bookmark")
    public CommonResponse deleteBookmark(@RequestBody UserCommand.Bookmark command) {
        userService.deleteBookmark(command);
        return CommonResponse.success("OK");
    }
}
