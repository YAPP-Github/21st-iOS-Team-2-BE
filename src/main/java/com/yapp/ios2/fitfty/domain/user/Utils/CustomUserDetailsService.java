package com.yapp.ios2.fitfty.domain.user.Utils;

import com.yapp.ios2.fitfty.domain.user.User;
import com.yapp.ios2.fitfty.domain.user.UserReader;
import com.yapp.ios2.fitfty.global.exception.MemberNotActivatedException;
import com.yapp.ios2.fitfty.global.exception.MemberNotFoundException;
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
    private final UserReader userReader;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) {
        return userReader.findOneByEmail(email)
                .map(user -> createUser(email, user))
                .orElseThrow(MemberNotFoundException::new);
    }

    private org.springframework.security.core.userdetails.User createUser(String username, User user) {
        if (!user.isActivated()) {
            throw new MemberNotActivatedException();
        }

        List<GrantedAuthority> grantedAuthorities = Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                                                                      user.getPassword(),
                                                                      grantedAuthorities);
    }
}