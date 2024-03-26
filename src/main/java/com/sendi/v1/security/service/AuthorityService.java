package com.sendi.v1.security.service;

import com.sendi.v1.security.domain.Authority;

import java.util.List;
import java.util.Optional;

public interface AuthorityService {
    Authority createOrUpdate(Authority authority);
    Authority getById(Long id);
    Authority getByPermission(String permission);
    long count();
    boolean existsByPermission(String permission);
}
