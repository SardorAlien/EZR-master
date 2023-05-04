package com.sendi.v1.security.service;

import com.sendi.v1.service.dto.UserDTO;
import com.sendi.v1.security.domain.Role;
import com.sendi.v1.security.domain.User;

import java.util.List;

public interface UserService {
    User createOrUpdate(User user) throws Exception;

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    User getUser(String username);

    List<UserDTO> getUsers();

    UserDTO getUserById(Long userId);

    long count();

    boolean existsByUsername(String username);
}
