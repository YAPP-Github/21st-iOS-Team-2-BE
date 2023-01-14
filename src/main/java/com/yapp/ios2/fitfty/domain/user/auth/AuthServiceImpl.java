package com.yapp.ios2.fitfty.domain.user.auth;

import com.yapp.ios2.fitfty.domain.user.UserCommand.SignIn;
import com.yapp.ios2.fitfty.domain.user.auth.Utils.JwtTokenProvider;
import com.yapp.ios2.fitfty.global.exception.MemberNotFoundException;
import com.yapp.ios2.fitfty.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    public String authorize(SignIn command) {

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
}
