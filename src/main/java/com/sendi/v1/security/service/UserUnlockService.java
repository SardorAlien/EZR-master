package com.sendi.v1.security.service;

import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserUnlockService {
    private final UserRepository userRepository;

    @Scheduled(fixedRate = 86400000)
    public void unlockAccounts() {
        log.info("Running Unlock Accounts");

        List<User> lockedUsers = userRepository
                .findAllByAccountNonLockedAndLastModifiedTimeIsBefore(false,
                        LocalDate.now().minusDays(1L));

        if (lockedUsers.size() > 0) {
            log.info("Locked users found, unlocking : ");
            lockedUsers.forEach(user -> user.setAccountNonLocked(true));

            userRepository.saveAll(lockedUsers);
        }
    }
}
