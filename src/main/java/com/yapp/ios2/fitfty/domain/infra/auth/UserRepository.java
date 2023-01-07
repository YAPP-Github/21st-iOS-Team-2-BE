package com.yapp.ios2.fitfty.domain.infra.auth;
import com.yapp.ios2.fitfty.domain.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByUsername(String username);
}