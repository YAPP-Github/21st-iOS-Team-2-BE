package com.yapp.ios2.fitfty.domain.auth.Utils;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter()
                .println("{ \n" +
                                 "\t\"result\": \"FAIL\",\n" +
                                 "  \"data\": null,\n" +
                                 "  \"message\" : \"로그인이 필요한 요청입니다.\",\n" +
                                 "  \"errorCode\" : \"UNAUTHORIZED\"\n" +
                                 "}");
    }
}