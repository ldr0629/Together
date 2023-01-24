package com.study.joiner.repository;

import com.study.joiner.domain.user.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SocialUser, Long> {
    Optional<SocialUser> findByEmail(String email);

    @Query("select u from SocialUser u left join fetch u.boardList where u.email = :email")
    Optional<SocialUser> findByEmailFetchBL(@Param("email") String email);

    boolean existsByNickName(String nickName);
}
