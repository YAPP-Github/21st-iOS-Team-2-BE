package com.yapp.ios2.fitfty.domain.user;

import com.yapp.ios2.fitfty.global.exception.MemberAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserReader userReader;
    private final UserStore userStore;

    @Override
    @Transactional
    public String registerUser(UserCommand.SignUp command) {
        if (userReader.findOneByEmail(command.getEmail()).orElse(null) != null) {
            throw new MemberAlreadyExistException();
        }

        User user = User.builder()
                .email(command.getEmail())
                .type(command.getType())
                .build();

        return userStore.store(user).getUserToken();
    }
}
