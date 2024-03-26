package com.sendi.v1.security.service;

import com.sendi.v1.exception.custom.NoSuchRoleException;
import com.sendi.v1.exception.custom.RoleDuplicationException;
import com.sendi.v1.security.domain.Role;
import com.sendi.v1.security.repo.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role createOrUpdate(Role role) {
        roleRepository.findByName(role.getName())
                .ifPresent((existingRole) -> {
                    throw new RoleDuplicationException(existingRole.getName());
                });

        return roleRepository.save(role);
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new NoSuchRoleException(id));
    }

    @Override
    public Role getByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new NoSuchRoleException(name));
    }

    @Override
    public long count() {
        return roleRepository.count();
    }

    @Override
    public boolean existsByRoleName(String roleName) {
        return roleRepository.existsByName(roleName);
    }
}
