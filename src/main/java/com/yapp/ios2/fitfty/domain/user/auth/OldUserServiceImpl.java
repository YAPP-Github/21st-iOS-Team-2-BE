package com.yapp.ios2.fitfty.domain.user.auth;

import com.yapp.ios2.fitfty.domain.user.User;
import com.yapp.ios2.fitfty.domain.user.UserReader;
import com.yapp.ios2.fitfty.domain.user.UserStore;
import com.yapp.ios2.fitfty.global.exception.MemberAlreadyExistException;
import com.yapp.ios2.fitfty.global.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OldUserServiceImpl {

    private final UserReader userReader;
    private final UserStore userStore;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto signup(UserDto userDto) {
        if (userReader.findOneByEmail(userDto.getEmail())
                .orElse(null) != null) {
//            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
//            common exception code merge 이후 선언해서 사용 예정
            throw new MemberAlreadyExistException();
        }

        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .role("ROLE_USER")
                .activated(true)
                .build();

        return UserDto.from(userStore.store(user));
    }

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