package com.simple.bank.service;

import com.simple.bank.domain.entities.User;
import com.simple.bank.dto.UserDTO;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    User registerUser(UserDTO UserDTO);
}
