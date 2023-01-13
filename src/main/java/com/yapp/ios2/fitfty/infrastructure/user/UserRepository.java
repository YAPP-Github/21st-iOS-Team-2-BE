package com.yapp.ios2.fitfty.infrastructure.user;

import com.yapp.ios2.fitfty.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByEmail(String email);
}