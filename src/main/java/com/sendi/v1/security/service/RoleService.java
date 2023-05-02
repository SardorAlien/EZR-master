package com.sendi.v1.security.service;

import com.sendi.v1.security.domain.Role;

import java.util.List;

public interface RoleService {
    Role createOrUpdate(Role role);
    Role getById(Long id);
    Role getByName(String name);
    long count();
}
