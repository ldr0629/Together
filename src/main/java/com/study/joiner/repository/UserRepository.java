package com.study.joiner.repository;

import com.study.joiner.domain.user.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SocialUser, Long> {
    Optional<SocialUser> findByEmail(String email);
}
