package com.yapp.ios2.fitfty.domain.user.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.util.StringUtils;
import com.yapp.ios2.fitfty.domain.board.BoardReader;
import com.yapp.ios2.fitfty.domain.board.BoardStore;
import com.yapp.ios2.fitfty.domain.user.User.LoginType;
import com.yapp.ios2.fitfty.domain.user.UserCommand;
import com.yapp.ios2.fitfty.domain.user.UserCommand.SignIn;
import com.yapp.ios2.fitfty.domain.user.UserCommand.SignUp;
import com.yapp.ios2.fitfty.domain.user.UserInfo;
import com.yapp.ios2.fitfty.domain.user.UserReader;
import com.yapp.ios2.fitfty.domain.user.UserService;
import com.yapp.ios2.fitfty.domain.user.UserStore;
import com.yapp.ios2.fitfty.domain.user.Utils.JwtTokenProvider;
import com.yapp.ios2.fitfty.global.exception.KakaoOAuthException;
import com.yapp.ios2.fitfty.global.exception.MemberNotFoundException;
import com.yapp.ios2.fitfty.global.response.ErrorCode;
import com.yapp.ios2.fitfty.infrastructure.user.OAuth.ApplePublicKeyResponse;
import com.yapp.ios2.fitfty.infrastructure.user.OAuth.KakaoOAuth;
import com.yapp.ios2.fitfty.infrastructure.user.OAuth.MaterialsOfApplePublicKey;
import com.yapp.ios2.fitfty.interfaces.user.UserDto.KakaoOAuthTokenDto;
import com.yapp.ios2.fitfty.interfaces.user.UserDto.KakaoProfileDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final KakaoOAuth kakaoOAuth;
    private final UserReader userReader;
    private final UserService userService;
    private final UserStore userStore;
    private final BoardReader boardReader;
    private final BoardStore boardStore;

    @Override
    public String login(UserCommand.SignIn command) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(command.getEmail(),
                                                        command.getPassword());

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

        return jwtTokenProvider.createToken(authentication);
    }

    @Override
    @Transactional
    public String loginWithKakaoCode(String code) {
        // 1. kakaoCallback 으로부터 code 받아온걸로 kakaoToken GET
        KakaoOAuthTokenDto kakaoOAuthTokenDto = kakaoOAuth.getOAuthToken(code);

        // 2. kakaoToken 으로 profile, email GET
        KakaoProfileDto kakaoProfileDto = kakaoOAuth.getProfile(
                kakaoOAuthTokenDto.getAccess_token());
        SignUp signUp = new SignUp(kakaoProfileDto.kakaoAccount.email, LoginType.KAKAO);

        if (signUp.getEmail() == null) {
            throw new KakaoOAuthException(ErrorCode.NO_EMAIL.getErrorMsg());
        }

        // 3. 가입 여부 확인하고, 가입안되어있으면 가입
        if (userReader.findFirstByEmail(signUp.getEmail())
                .orElse(null) == null) {
            var userInit = userService.registerUser(signUp);
            userInit.updateKakaoLoginInfo(kakaoProfileDto);
        }

        // 4. login 처리 후 token 반환
        return login(SignIn.builder()
                             .email(signUp.getEmail())
                             .password(signUp.getPassword())
                             .build());
    }

    @Override
    @Transactional
    public UserInfo.SignInInfo loginWithKakao(UserCommand.SignInKakao command) {
        KakaoProfileDto kakaoProfileDto = kakaoOAuth.getProfile(command.getAccessToken());
        SignUp signUp = new SignUp(kakaoProfileDto.kakaoAccount.email, LoginType.KAKAO);

        if (signUp.getEmail() == null) {
            throw new KakaoOAuthException(ErrorCode.NO_EMAIL.getErrorMsg());
        }

        boolean isNew = false;
        if (userReader.findFirstByEmail(signUp.getEmail())
                .orElse(null) == null) {
            var userInit = userService.registerUser(signUp);
            userInit.updateKakaoLoginInfo(kakaoProfileDto);
            isNew = true;
        }

        var authToken = login(SignIn.builder()
                             .email(signUp.getEmail())
                             .password(signUp.getPassword())
                             .build());
        return new UserInfo.SignInInfo(authToken, isNew);
    }

    @Override
    @Transactional
    public UserInfo.SignInInfo loginWithApple(UserCommand.SignInApple command) {
        if (StringUtils.isNullOrEmpty(command.getUserEmail())) {
            String headerOfAccessToken = command.getIdentityToken().substring(0, command.getIdentityToken().indexOf("."));

            String kidAndAlg = new String(Base64.getDecoder().decode(headerOfAccessToken));
            ObjectMapper objectMapper = new ObjectMapper();
            Map mappedKidAlg = null;
            try {
                mappedKidAlg = objectMapper.readValue(kidAndAlg,Map.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            // 애플한테서 key들의 재료들 얻기 + 알맞는 key의 재료 찾기
            ApplePublicKeyResponse keys = ApplePublicKeyResponse.getApplePublicKeys();
            MaterialsOfApplePublicKey key = keys.getMatchedKeyBy(mappedKidAlg.get("kid"), mappedKidAlg.get("alg"))
                    .orElseThrow(() -> new NullPointerException("Failed get public key from apple's id server."));

            // 알맞는 key 재료로 부터, RS256 ( SHA-256 + RSA ) 암호화방식에서 사용하는 n, e 구하기
            byte[] nBytes = Base64.getUrlDecoder().decode(key.getN());
            byte[] eBytes = Base64.getUrlDecoder().decode(key.getE());
            BigInteger n = new BigInteger(1, nBytes);
            BigInteger e = new BigInteger(1, eBytes);

            // n, e를 이용하여 public key 만들기
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
            KeyFactory keyFactory = null;
            PublicKey publicKey = null;

            try{
                keyFactory = KeyFactory.getInstance(key.getKty());
                publicKey = keyFactory.generatePublic(publicKeySpec);
            }
            catch (Exception e2){
                e2.printStackTrace();
            }

            // 만들어진 public key로 accessToken의 body를 디코딩해서 유효한 유저 프로필 얻기
            Claims userInfo = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(command.getIdentityToken()).getBody();

            command.setUserEmail(userInfo.get("email", String.class));
        }

        SignUp signUp = new SignUp(command.getUserEmail(), LoginType.APPLE);

        boolean isNew = false;
        if (userReader.findFirstByEmail(signUp.getEmail())
                .orElse(null) == null) {
            userService.registerUser(signUp);
            isNew = true;
        }

        var authToken = login(SignIn.builder()
                                      .email(signUp.getEmail())
                                      .password(signUp.getPassword())
                                      .build());
        return new UserInfo.SignInInfo(authToken, isNew);
    }

    @Override
    public String getRole() {
        var userToken = userService.getCurrentUserToken();
        var user = userReader.findFirstByUserToken(userToken);
        return user.getRole();
    }

    @Override
    @Transactional
    public void unActivateUser() {
        var userToken = userService.getCurrentUserToken();
        var user = userReader.findFirstByUserToken(userToken);
        var feeds = userReader.findFeedByUserToken(userToken);
        var boards = feeds.stream()
                .map(feed -> boardReader.getBoard(feed.getBoardToken()))
                .peek(boardStore::deleteBoard)
                .collect(Collectors.toList());
        userStore.delete(user);
    }
}
