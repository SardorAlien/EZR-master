package com.sendi.v1.security.service;

import com.sendi.v1.exception.custom.NoSuchRoleException;
import com.sendi.v1.exception.custom.RoleDuplicationException;
import com.sendi.v1.security.domain.Role;
import com.sendi.v1.security.repo.RoleRepository;
import com.sendi.v1.util.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role createOrUpdate(Role role) {
        roleRepository.findByName(role.getName())
                .ifPresent((existingRole) -> {
                    throw new RoleDuplicationException(ErrorMessages.ROLE_DUPLICATION.getMessage() + existingRole.getName());
                });

        return roleRepository.save(role);
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new NoSuchRoleException(ErrorMessages.NO_SUCH_ROLE_ID.getMessage() + id));
    }

    @Override
    public Role getByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new NoSuchRoleException(ErrorMessages.NO_SUCH_ROLE.getMessage() + name));
    }

    @Override
    public long count() {
        return roleRepository.count();
    }
}
