package com.simple.bank.repository;

import com.simple.bank.domain.entities.Role;
import com.simple.bank.domain.enums.RoleTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRoleName(RoleTypes roleName);
}
