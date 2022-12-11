package com.yapp.ios2.fitfty.domain.hello.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HelloService {
    public String getHello(String id){
        if ( id != null ){
            return "HELLO : " + id;
        }

        return "HELLO";
    }
}
