package com.yapp.ios2.fitfty.infrastructure.user;

import com.yapp.ios2.fitfty.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
