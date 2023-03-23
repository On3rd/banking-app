package com.simple.bank.service.impl;

import com.simple.bank.domain.entities.Role;
import com.simple.bank.domain.entities.User;
import com.simple.bank.domain.enums.RoleTypes;
import com.simple.bank.dto.UserDTO;
import com.simple.bank.repository.UserRepository;
import com.simple.bank.service.RoleService;
import com.simple.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
    @Override
    public User registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Set<Role> roles = generateRolesSet();
        user.setRoles(roles);
        user.setAccounts(new ArrayList<>());
        return userRepository.save(user);
    }
    private Set<Role> generateRolesSet() {
        Role role = roleService.findByRoleName(RoleTypes.USER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        return roles;
    }
    public boolean hasValidPassword(User user, String pwd) {
        return passwordEncoder.matches(pwd, user.getPassword());
    }
}
