package com.yapp.ios2.fitfty.domain.user.auth.Utils;

import com.yapp.ios2.fitfty.domain.user.User;
import com.yapp.ios2.fitfty.global.exception.MemberNotActivated;
import com.yapp.ios2.fitfty.global.exception.MemberNotFoundException;
import com.yapp.ios2.fitfty.infrastructure.user.UserRepository;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) {
        return userRepository.findOneByEmail(email)
                .map(user -> createUser(email, user))
                .orElseThrow(MemberNotFoundException::new);
    }

    private org.springframework.security.core.userdetails.User createUser(String username, User user) {
        if (!user.isActivated()) {
            throw new MemberNotActivated();
        }

        List<GrantedAuthority> grantedAuthorities = Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                                                                      user.getPassword(),
                                                                      grantedAuthorities);
    }
}