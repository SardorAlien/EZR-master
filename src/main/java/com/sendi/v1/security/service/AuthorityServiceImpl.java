package com.sendi.v1.security.service;

import com.sendi.v1.exception.custom.AuthorityDuplicationException;
import com.sendi.v1.exception.custom.NoSuchAuthorityException;
import com.sendi.v1.security.domain.Authority;
import com.sendi.v1.security.repo.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {
    private final AuthorityRepository authorityRepository;

    @Override
    public Authority createOrUpdate(Authority authority) {
        authorityRepository.findByPermission(authority.getPermission())
                .ifPresent((existingAuthority) -> {
                    throw new AuthorityDuplicationException(existingAuthority.getPermission());
                });
        return authorityRepository.save(authority);
    }

    @Override
    public Authority getById(Long id) {
        return authorityRepository.findById(id)
                .orElseThrow(() -> new NoSuchAuthorityException(id));
    }

    @Override
    public Authority getByPermission(String permission) {
        return authorityRepository.findByPermission(permission)
                .orElseThrow(() -> new NoSuchAuthorityException(permission));
    }

    @Override
    public long count() {
        return authorityRepository.count();
    }

    @Override
    public boolean existsByPermission(String permission) {
        return authorityRepository.existsByPermission(permission);
    }
}
