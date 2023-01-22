package com.yapp.ios2.fitfty.interfaces.user;

import static com.yapp.ios2.fitfty.global.util.Constants.API_PREFIX;

import com.yapp.ios2.fitfty.domain.user.UserCommand;
import com.yapp.ios2.fitfty.domain.user.UserMapper;
import com.yapp.ios2.fitfty.domain.user.UserService;
import com.yapp.ios2.fitfty.domain.user.auth.AuthService;
import com.yapp.ios2.fitfty.domain.user.auth.OldUserServiceImpl;
import com.yapp.ios2.fitfty.global.response.CommonResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(API_PREFIX + "/auth")
public class AuthController {

    private final OldUserServiceImpl oldUserService;
    private final UserService userService;
    private final AuthService authService;
    private final UserMapper userMapper;

    @PostMapping("/sign-in")
    public CommonResponse authorize(@Valid @RequestBody UserDto.SignInDto signInDto) {
        log.debug("/auth/sign-in" + signInDto.toString());

        return CommonResponse.success(authService.login(userMapper.toSignIn(signInDto)));
    }

    @PostMapping("/sign-up")
    public CommonResponse signup(@Valid @RequestBody UserDto.SignUpDto signUpDto
    ) {
        return CommonResponse.success(userService.registerUser(userMapper.toSignUp(signUpDto)));
    }

    @PostMapping("/logout")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse logout() {
        return CommonResponse.success("LOGOUT");
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse deleteAccount() {
        return CommonResponse.success("/auth/delete");
    }

    @GetMapping("/kakao/callback")
    public CommonResponse HandleKakakoOAuth(String code) {
        return CommonResponse.success(authService.loginWithKakao(code));
    }

    @DeleteMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse unActivateUser() {
        authService.unActivateUser();
        return CommonResponse.success("OK");
    }

    // AUTH TEST API
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse getMyUserInfo() {
        return CommonResponse.success(oldUserService.getMyUser());
    }

    @GetMapping("/user/{email}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public CommonResponse getUserInfo(@PathVariable String email) {
        return CommonResponse.success(oldUserService.getUser(email));
    }
}