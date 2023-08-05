package com.sendi.v1.security.repo;

import com.sendi.v1.security.domain.LoginFailure;
import com.sendi.v1.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface LoginFailureRepository extends JpaRepository<LoginFailure, Long> {
    List<LoginFailure> findAllByUserAndCreatedAtIsAfter(User user, LocalDate localDate);
}
