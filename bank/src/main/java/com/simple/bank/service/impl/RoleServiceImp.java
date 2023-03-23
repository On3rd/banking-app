package com.simple.bank.service.impl;

import com.simple.bank.domain.entities.Role;
import com.simple.bank.domain.enums.RoleTypes;
import com.simple.bank.repository.RoleRepository;
import com.simple.bank.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByRoleName(RoleTypes roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
