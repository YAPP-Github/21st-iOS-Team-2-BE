package com.yapp.ios2.fitfty.interfaces.auth;

import com.yapp.ios2.fitfty.domain.auth.Utils.JwtTokenProvider;
import com.yapp.ios2.fitfty.domain.auth.UserDto;
import com.yapp.ios2.fitfty.domain.auth.UserService;
import com.yapp.ios2.fitfty.global.exception.MemberNotFoundException;
import com.yapp.ios2.fitfty.global.response.CommonResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;

    @PostMapping("/auth/sign-in")
    public CommonResponse authorize(@Valid @RequestBody SignInDto signInDto) {
        log.debug("/auth/sign-in" + signInDto.toString());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(signInDto.getUsername(),
                                                        signInDto.getPassword());

        Authentication authentication;

        // .authenticate(authenticationtoken) 실행 시
        // 내부적으로 CustomUserDetailsService -> @Overload loadUserByUserName Method 실행 됨
        try {
            authentication = authenticationManagerBuilder.getObject()
                    .authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new MemberNotFoundException();
        }

        // 생성된 Authentication 객체를 이용하여 1) SecurityContextHolder, 2)jwt Token 생성해서 리턴
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        return CommonResponse.success(jwtTokenProvider.createToken(authentication));
    }

    @PostMapping("/auth/sign-up")
    public CommonResponse signup(
            @Valid @RequestBody UserDto userDto
    ) {
        return CommonResponse.success(userService.signup(userDto));
    }

    // AUTH TEST API
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse getMyUserInfo() {
        return CommonResponse.success(userService.getMyUser());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public CommonResponse getUserInfo(@PathVariable String username) {
        return CommonResponse.success(userService.getUser(username));
    }

    @GetMapping("/auth/kakao/callback")
    public CommonResponse HandleKakakoOAuth(String code) {

        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "31e815075078c89d7505decdeed8af98");
        params.add("redirect_uri", "http://localhost:8080/api/v1/auth/kakao/callback");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> responseEntity = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        return CommonResponse.success(responseEntity);
    }
}