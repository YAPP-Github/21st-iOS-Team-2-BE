package com.yapp.ios2.fitfty.domain.user.Utils;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter()
                .println("{ \n" +
                                 "\t\"result\": \"FAIL\",\n" +
                                 "  \"data\": null,\n" +
                                 "  \"message\" : \"자원에 접근할 권한이 없습니다.\",\n" +
                                 "  \"errorCode\" : \"FORBIDDEN\"\n" +
                                 "}");
    }
}
