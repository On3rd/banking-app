package com.simple.bank.service;

import com.simple.bank.domain.entities.Role;
import com.simple.bank.domain.enums.RoleTypes;

public interface RoleService {
    Role findByRoleName(RoleTypes roleName);
}
