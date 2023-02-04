package com.yapp.ios2.fitfty.domain.user.auth;

import com.yapp.ios2.fitfty.domain.user.User.LoginType;
import com.yapp.ios2.fitfty.domain.user.UserCommand;
import com.yapp.ios2.fitfty.domain.user.UserCommand.SignIn;
import com.yapp.ios2.fitfty.domain.user.UserCommand.SignUp;
import com.yapp.ios2.fitfty.domain.user.UserReader;
import com.yapp.ios2.fitfty.domain.user.UserService;
import com.yapp.ios2.fitfty.domain.user.UserStore;
import com.yapp.ios2.fitfty.domain.user.Utils.JwtTokenProvider;
import com.yapp.ios2.fitfty.global.exception.InvalidParamException;
import com.yapp.ios2.fitfty.global.exception.MemberNotFoundException;
import com.yapp.ios2.fitfty.infrastructure.user.OAuth.KakaoOAuth;
import com.yapp.ios2.fitfty.interfaces.user.UserDto.KakaoOAuthTokenDto;
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
    public String loginWithKakaoCode(String code) {
        // 1. kakaoCallback 으로부터 code 받아온걸로 kakaoToken GET
        KakaoOAuthTokenDto kakaoOAuthTokenDto = kakaoOAuth.getOAuthToken(code);

        // 2. kakaoToken 으로 profile, email GET
        SignUp signUp = kakaoOAuth.getProfile(kakaoOAuthTokenDto.getAccess_token());

        if (signUp.getEmail() == null) {
            throw new InvalidParamException("EMAIL 조회 권한 승인이 필요합니다.");
        }

        // 3. 가입 여부 확인하고, 가입안되어있으면 가입
        if (userReader.findOneByEmail(signUp.getEmail())
                .orElse(null) == null) {
            userService.registerUser(signUp);
        }

        // 4. login 처리 후 token 반환
        return login(SignIn.builder()
                             .email(signUp.getEmail())
                             .password(signUp.getPassword())
                             .build());
    }

    @Override
    public String loginWithKakao(UserCommand.SignInKakao command) {
        SignUp signUp = kakaoOAuth.getProfile(command.getAccessToken());

        if (signUp.getEmail() == null) {
            throw new InvalidParamException("EMAIL 조회 권한 승인이 필요합니다.");
        }

        if (userReader.findOneByEmail(signUp.getEmail())
                .orElse(null) == null) {
            userService.registerUser(signUp);
        }

        return login(SignIn.builder()
                             .email(signUp.getEmail())
                             .password(signUp.getPassword())
                             .build());
    }

    @Override
    public String loginWithApple(UserCommand.SignInApple command) {
        if (command.getUserEmail() == null) {
            // EMAIL PUBLIC KEY 가져오고 command.eamil에 넣어주는 로직 필요
            throw new InvalidParamException("EMAIL 조회 권한 승인이 필요합니다.");
        }

        SignUp signUp = new SignUp(command.getUserEmail(), LoginType.APPLE);

        if (userReader.findOneByEmail(signUp.getEmail())
                .orElse(null) == null) {
            userService.registerUser(signUp);
        }

        return login(SignIn.builder()
                             .email(signUp.getEmail())
                             .password(signUp.getPassword())
                             .build());
    }

    @Override
    @Transactional
    public void unActivateUser() {
        var userToken = userService.getCurrentUserToken();
        var user = userReader.findOneByUserToken(userToken);
        user.deleteUser();
        userStore.store(user);
    }
}
