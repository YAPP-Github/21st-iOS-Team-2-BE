package com.yapp.ios2.fitfty.interfaces.user;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LocalTestController {
    @GetMapping(value = "/api/v1/users/kakao", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String welcomeAsHTML() {
        return "<html>\n" + "<header><title>Welcome</title></header>\n" +
                "<body>\n" + "<a href=https://kauth.kakao.com/oauth/authorize?client_id=31e815075078c89d7505decdeed8af98&redirect_uri=http://52.79.144.104:8080/api/v1/auth/kakao/callback&response_type=code>KAKAO LOGIN BUTTON</a>\n" + "</body>\n" + "</html>";
    }
}
