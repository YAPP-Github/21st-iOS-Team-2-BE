package com.yapp.ios2.fitfty.domain.hello.controller;

import com.yapp.ios2.fitfty.domain.hello.service.HelloService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HelloController {

    private final HelloService helloService;

    @GetMapping("/hello")
    public String printHello(@RequestParam(name = "id", required = false) String id) {
        log.info("GET /hello HTTP/1.1");

        return helloService.getHello(id);
    }
}
