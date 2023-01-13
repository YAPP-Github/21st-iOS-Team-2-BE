package com.yapp.ios2.fitfty.interfaces.user;

import static com.yapp.ios2.fitfty.global.util.Constants.API_PREFIX;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios2.fitfty.domain.user.auth.Utils.JwtTokenProvider;
import com.yapp.ios2.fitfty.domain.user.auth.UserDto;
import com.yapp.ios2.fitfty.domain.user.auth.OldUserServiceImpl;
import com.yapp.ios2.fitfty.global.exception.MemberNotFoundException;
import com.yapp.ios2.fitfty.global.response.CommonResponse;
import com.yapp.ios2.fitfty.interfaces.user.UserDto.KakaoOAuthTokenDto;
import com.yapp.ios2.fitfty.interfaces.user.UserDto.KakaoProfileDto;
import com.yapp.ios2.fitfty.interfaces.user.UserDto.SignInDto;
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
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping(API_PREFIX + "/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final OldUserServiceImpl userService;

    @PostMapping("/sign-in")
    public CommonResponse authorize(@Valid @RequestBody SignInDto signInDto) {
        log.debug("/auth/sign-in" + signInDto.toString());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(signInDto.getEmail(),
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

    @PostMapping("/sign-up")
    public CommonResponse signup(
            @Valid @RequestBody UserDto userDto
    ) {
        return CommonResponse.success(userService.signup(userDto));
    }

    @PostMapping("/logout")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public CommonResponse logout() {
        return CommonResponse.success("LOGOUT");
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public CommonResponse deleteAccount() {
        return CommonResponse.success("/auth/delete");
    }

    @GetMapping("/kakao/callback")
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

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoOAuthTokenDto kakaoOAuthTokenDto = null;
        try{
            kakaoOAuthTokenDto = objectMapper.readValue(responseEntity.getBody(),
                                                        KakaoOAuthTokenDto.class);
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("kakao access token : " + kakaoOAuthTokenDto.getAccess_token());


        RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers2.add("Authorization", "Bearer " + kakaoOAuthTokenDto.getAccess_token());

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest2 = new HttpEntity<>(headers2);

        ResponseEntity<String> responseEntity2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoTokenRequest2,
                String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfileDto kakaoProfileDto = null;
        try{
            kakaoProfileDto = objectMapper2.readValue(responseEntity2.getBody(),
                                                        KakaoProfileDto.class);
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(kakaoProfileDto.properties.nickname);
        System.out.println(kakaoProfileDto.kakaoAccount.email);

        return CommonResponse.success(kakaoProfileDto);
    }

    // AUTH TEST API
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse getMyUserInfo() {
        return CommonResponse.success(userService.getMyUser());
    }

    @GetMapping("/user/{email}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public CommonResponse getUserInfo(@PathVariable String email) {
        return CommonResponse.success(userService.getUser(email));
    }
}