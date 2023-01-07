package com.yapp.ios2.fitfty.domain.auth;


import com.yapp.ios2.fitfty.domain.infra.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto signup(UserDto userDto) {
        if (userRepository.findOneByUsername(userDto.getUsername())
                .orElse(null) != null) {
//            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
//            common exception code merge 이후 선언해서 사용 예정
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .role("ROLE_USER")
                .activated(true)
                .build();

        return UserDto.from(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserDto getUser(String username) {
        return UserDto.from(userRepository.findOneByUsername(username)
                                    .orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUser() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneByUsername)
                        .orElseThrow(() -> new RuntimeException("MEMBER NOT FOUND"))
                // TODO :
//                         common exception code merge 이후 선언해서 사용 예정
//                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }
}