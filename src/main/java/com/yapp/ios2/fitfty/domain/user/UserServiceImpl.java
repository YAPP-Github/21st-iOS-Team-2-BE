package com.yapp.ios2.fitfty.domain.user;

import com.yapp.ios2.fitfty.domain.user.UserInfo.CustomOption;
import com.yapp.ios2.fitfty.domain.user.UserInfo.UserFeed;
import com.yapp.ios2.fitfty.global.exception.CurrentContextError;
import com.yapp.ios2.fitfty.global.exception.MemberAlreadyExistException;
import com.yapp.ios2.fitfty.global.exception.MemberNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserReader userReader;
    private final UserStore userStore;
    private final UserMapper userMapper;

    @Override
    public String getCurrentUserToken() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new CurrentContextError();
        }

        return userReader.findOneByEmail(authentication.getName())
                .orElseThrow(() -> new MemberNotFoundException())
                .getUserToken();
    }

    @Override
    @Transactional
    public String registerUser(UserCommand.SignUp command) {
        if (userReader.findOneByEmail(command.getEmail())
                .orElse(null) != null) {
            throw new MemberAlreadyExistException();
        }

        User user = User.builder()
                .email(command.getEmail())
                .type(command.getType())
                .build();

        return userStore.store(user)
                .getUserToken();
    }

    @Override
    public String findNickname(String nickname) {

        Optional<User> result = userReader.findOneByNickname(nickname);

        if (result.orElse(null) == null) {
            return null;
        }
        return result.get()
                .getNickname();
    }

    @Override
    public CustomOption updateUserDetails(UserCommand.CustomOption command) {
        String userToken = getCurrentUserToken();
        User userInit = userReader.findOneByUserToken(userToken).orElseThrow(()-> new MemberNotFoundException());
        userInit.updateCustomOption(command);
        return userMapper.toCustomOption(userStore.store(userInit));
    }

    @Override
    public List<UserFeed> getUserFeed(String userToken) {
        List<Feed> feed = userReader.findByUserToken(userToken);
        return feed.stream().map(x -> userMapper.toUserFeed(x)).collect(Collectors.toList());
    }

    @Override
    public UserFeed addUserFeed(UserCommand.UserFeed userFeed) {
        return null;
    }

    @Override
    public UserFeed removeUserFeed(UserCommand.UserFeed userFeed) {
        return null;
    }
}