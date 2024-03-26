package com.sendi.v1.security.repo;

import com.sendi.v1.security.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long>{
    Optional<Authority> findByPermission(String permission);
    boolean existsByPermission(String permission);
}
