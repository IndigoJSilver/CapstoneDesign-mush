package com.project.capstonedesign.domain.user.repository;

import com.project.capstonedesign.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByRefreshToken(String refreshToken);
    Optional<User> findByOauthProviderAndOauthId(String oauthProvider, String oauthId);
}