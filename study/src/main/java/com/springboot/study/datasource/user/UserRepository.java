package com.springboot.study.datasource.user;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    Optional<User> findByEmail(String email);
}
