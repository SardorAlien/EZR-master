package com.sendi.v1.security.service;

import com.sendi.v1.dto.UserDTO;
import com.sendi.v1.dto.mapper.UserMapper;
import com.sendi.v1.security.domain.Role;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.repo.RoleRepository;
import com.sendi.v1.security.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public User saveUser(User user) {
        log.info("new user = {} is being saved", user);

        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("new role = {} is being saved", role);

        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("role {} is being added to a user with username = {}", roleName, username);

        Optional<User> optionalUser = userRepository.findByUsername(username);
        Optional<Role> optionalRole = roleRepository.findByName(roleName);

        if (optionalUser.isPresent() && optionalRole.isPresent()) {
            addRoleToUser(optionalUser.get(), optionalRole.get());
        }
    }

    private void addRoleToUser(User user, Role role) {
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("user is being fetched with username = {}", username);

        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with this username"));
    }

    @Override
    public List<User> getUsers() {
        log.info("all users are being fetched");

        return userRepository.findAll();
    }

    @Override
    public UserDTO getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid userId");
        }

        User user = userOptional.get();

        UserDTO newUserDTO = userMapper.toDTO(user);

        return newUserDTO;
    }
}
