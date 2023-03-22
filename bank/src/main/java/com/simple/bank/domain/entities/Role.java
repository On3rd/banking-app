package com.simple.bank.domain.entities;

import com.simple.bank.domain.enums.RoleTypes;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="role_id")
    private int id;
    @Column(name="role")
    private RoleTypes roleName;

    public Role() {
    }

    public Role(RoleTypes roleName) {
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleTypes getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleTypes roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return roleName.name();
    }
}
