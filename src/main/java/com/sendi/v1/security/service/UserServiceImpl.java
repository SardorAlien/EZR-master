package com.sendi.v1.security.service;

import com.sendi.v1.exception.custom.NoSuchUserException;
import com.sendi.v1.exception.custom.UserDuplicationException;
import com.sendi.v1.service.dto.UserDTO;
import com.sendi.v1.service.dto.mapper.UserMapper;
import com.sendi.v1.security.domain.Role;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.repo.RoleRepository;
import com.sendi.v1.security.repo.UserRepository;
import com.sendi.v1.util.ErrorMessages;
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
    public User createOrUpdate(User user) throws UserDuplicationException {
        log.info("new user = {} is being saved", user);

        userRepository.findByUsername(user.getUsername())
                .ifPresent((existingUser) -> {
                    throw new UserDuplicationException(ErrorMessages.USER_DUPLICATION.getMessage() + existingUser.getUsername());
                });

        userRepository.findByEmail(user.getEmail())
                .ifPresent((existingUser) -> {
                    throw new UserDuplicationException(ErrorMessages.USER_DUPLICATION_EMAIL.getMessage() + existingUser.getEmail());
                });

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

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchUserException(ErrorMessages.NO_SUCH_USER.getMessage() + username));
    }

    @Override
    public List<UserDTO> getUsers() {
        log.info("all users are being fetched");

        List<User> users = userRepository.findAll();

        List<UserDTO> userDTOList = userMapper.toUserDTOs(users);

        return userDTOList;
    }

    @Override
    public UserDTO getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                    new NoSuchUserException(ErrorMessages.NO_SUCH_USER_ID.getMessage() + id)
                );

        UserDTO newUserDTO = userMapper.toDTO(user);

        return newUserDTO;
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchUserException(ErrorMessages.NO_SUCH_USER.getMessage() + username));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
