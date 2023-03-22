package com.simple.bank.service;

import com.simple.bank.domain.entities.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
}
