package com.sendi.v1.security.repo;

import com.sendi.v1.security.domain.LoginSuccess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginSuccessRepository extends JpaRepository<LoginSuccess, Long> {
}
