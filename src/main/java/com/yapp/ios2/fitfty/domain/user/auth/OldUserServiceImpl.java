package com.yapp.ios2.fitfty.domain.user.auth;

import com.yapp.ios2.fitfty.domain.user.User;
import com.yapp.ios2.fitfty.global.exception.MemberAlreadyExistException;
import com.yapp.ios2.fitfty.global.exception.MemberNotFoundException;
import com.yapp.ios2.fitfty.infrastructure.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OldUserServiceImpl {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto signup(UserDto userDto) {
        if (userRepository.findOneByEmail(userDto.getEmail())
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

        return UserDto.from(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserDto getUser(String email) {
        return UserDto.from(userRepository.findOneByEmail(email)
                                    .orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUser() {
        return UserDto.from(
                SecurityService.getCurrentUsername()
                        .flatMap(userRepository::findOneByEmail)
                        .orElseThrow(MemberNotFoundException::new)
        );
    }
}