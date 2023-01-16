package com.yapp.ios2.fitfty.domain.user.auth;

import com.yapp.ios2.fitfty.domain.user.UserReader;
import com.yapp.ios2.fitfty.domain.user.auth.Utils.SecurityService;
import com.yapp.ios2.fitfty.global.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OldUserServiceImpl {
    // TODO: 카카오 로그인 구현 완료 후에, 정상적으로 권한 동작 테스트 되면 지우기
    private final UserReader userReader;

    @Transactional(readOnly = true)
    public OldUserDto getUser(String email) {
        return OldUserDto.from(userReader.findOneByEmail(email)
                                    .orElse(null));
    }

    @Transactional(readOnly = true)
    public OldUserDto getMyUser() {
        return OldUserDto.from(
                SecurityService.getCurrentUsername()
                        .flatMap(userReader::findOneByEmail)
                        .orElseThrow(MemberNotFoundException::new)
        );
    }
}