package com.yapp.ios2.fitfty.interfaces.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yapp.ios2.fitfty.global.util.Constants.API_PREFIX;

@Slf4j
@RestController
@RequestMapping(API_PREFIX + "/users")
public class UserApiController {
}
