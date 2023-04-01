package com.sendi.v1.security.service;

import com.sendi.v1.dto.UserDTO;
import com.sendi.v1.security.domain.Role;
import com.sendi.v1.security.domain.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();
    UserDTO getUserById(Long userId);
}
