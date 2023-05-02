package com.sendi.v1.security.service;

import com.sendi.v1.exception.custom.AuthorityDuplicationException;
import com.sendi.v1.exception.custom.NoSuchAuthorityException;
import com.sendi.v1.security.domain.Authority;
import com.sendi.v1.security.repo.AuthorityRepository;
import com.sendi.v1.util.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {
    private final AuthorityRepository authorityRepository;

    @Override
    public Authority createOrUpdate(Authority authority) {
        authorityRepository.findByPermission(authority.getPermission())
                .ifPresent((existingAuthority) -> {
                    throw new AuthorityDuplicationException(ErrorMessages.AUTHORITY_DUPLICATION.getMessage() + existingAuthority.getPermission());
                });
        return authorityRepository.save(authority);
    }

    @Override
    public Authority getById(Long id) {
        return authorityRepository.findById(id)
                .orElseThrow(() -> new NoSuchAuthorityException(ErrorMessages.NO_SUCH_AUTHORITY_ID.getMessage() + id));
    }

    @Override
    public Authority getByPermission(String permission) {
        return authorityRepository.findByPermission(permission)
                .orElseThrow(() -> new NoSuchAuthorityException(ErrorMessages.NO_SUCH_AUTHORITY.getMessage() + permission));
    }

    @Override
    public long count() {
        return authorityRepository.count();
    }
}
