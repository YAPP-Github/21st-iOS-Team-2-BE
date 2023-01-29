package com.yapp.ios2.fitfty.infrastructure.user.OAuth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios2.fitfty.domain.user.User.LoginType;
import com.yapp.ios2.fitfty.domain.user.UserCommand.SignUp;
import com.yapp.ios2.fitfty.interfaces.user.UserDto.KakaoOAuthTokenDto;
import com.yapp.ios2.fitfty.interfaces.user.UserDto.KakaoProfileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoOAuth {

    public KakaoOAuthTokenDto getOAuthToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "31e815075078c89d7505decdeed8af98");
        params.add("redirect_uri", "http://52.79.144.104:8080/api/v1/auth/kakao/callback");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoOAuthTokenDto kakaoOAuthTokenDto = null;
        try {
            kakaoOAuthTokenDto = objectMapper.readValue(responseEntity.getBody(),
                                                        KakaoOAuthTokenDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return kakaoOAuthTokenDto;
    }

    public SignUp getProfile(KakaoOAuthTokenDto kakaoOAuthTokenDto) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization", "Bearer " + kakaoOAuthTokenDto.getAccess_token());

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfileDto kakaoProfileDto = null;
        try {
            kakaoProfileDto = objectMapper.readValue(responseEntity.getBody(),
                                                      KakaoProfileDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SignUp.builder()
                .email(kakaoProfileDto.kakaoAccount.email)
                .password("FITFTY_USER")
                .type(LoginType.KAKAO)
                .build();
    }
}
