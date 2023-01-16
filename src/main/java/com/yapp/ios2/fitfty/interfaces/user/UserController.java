package com.yapp.ios2.fitfty.interfaces.user;

import static com.yapp.ios2.fitfty.global.util.Constants.API_PREFIX;

import com.yapp.ios2.fitfty.domain.user.UserService;
import com.yapp.ios2.fitfty.global.response.CommonResponse;
import com.yapp.ios2.fitfty.interfaces.user.UserDto.CustomOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        log.info("CURRENT USER : " + userService.getCurrentUserToken());
        return CommonResponse.success(userService.findNickname(nickname));
    }

    @PutMapping("/details")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse updateUserDetails(@RequestBody CustomOption request) {
        var userCommand = userDtoMapper.of(request);
        var response = userService.updateUserDetails(userCommand);
        return CommonResponse.success(response);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse getMyProfile() {
        return CommonResponse.success("/users/profile");
    }

    @PutMapping("/profile")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse updateMyProfile() {
        return CommonResponse.success("/users/profile");
    }

    @GetMapping("/profile/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse getProfile() {
        return CommonResponse.success("/users/profile/{id}");
    }

    @PutMapping("/style")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse updateMyStyle() {
        return CommonResponse.success("/users/style");
    }

    @GetMapping("/bookmark/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse getBookmark() {
        return CommonResponse.success("/users/bookmark/{id}");
    }
}
