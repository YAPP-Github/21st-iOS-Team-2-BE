package com.yapp.ios2.fitfty.domain.user.auth;

import com.yapp.ios2.fitfty.domain.user.UserReader;
import com.yapp.ios2.fitfty.global.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OldUserServiceImpl {

    private final UserReader userReader;

    @Transactional(readOnly = true)
    public UserDto getUser(String email) {
        return UserDto.from(userReader.findOneByEmail(email)
                                    .orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUser() {
        return UserDto.from(
                SecurityService.getCurrentUsername()
                        .flatMap(userReader::findOneByEmail)
                        .orElseThrow(MemberNotFoundException::new)
        );
    }
}