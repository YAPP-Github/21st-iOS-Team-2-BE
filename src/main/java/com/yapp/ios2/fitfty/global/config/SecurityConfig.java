package com.yapp.ios2.fitfty.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // h2 iframe 관련 허용
        http.headers()
                .frameOptions()
                .sameOrigin();

        // 일단 csrf 전체 풀어 둠
        http.csrf()
                .disable();
    }
}
