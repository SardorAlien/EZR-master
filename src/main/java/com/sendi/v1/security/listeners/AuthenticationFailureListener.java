package com.sendi.v1.security.listeners;

import com.sendi.v1.exception.custom.TooManyAuthenticationFailuresException;
import com.sendi.v1.security.domain.LoginFailure;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.repo.LoginFailureRepository;
import com.sendi.v1.security.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFailureListener {
    private final UserRepository userRepository;

    private final LoginFailureRepository loginFailureRepository;

    @EventListener
    public void listen(AuthenticationFailureBadCredentialsEvent event) {
        if (event.getSource() instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getSource();
            LoginFailure.LoginFailureBuilder loginFailureBuilder = LoginFailure.builder();

            if (token.getPrincipal() instanceof String) {
                String username = (String) token.getPrincipal();
                loginFailureBuilder.username(username);

                userRepository.findByUsername(username).ifPresent(loginFailureBuilder::user);
            }

            Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();

            if (details instanceof WebAuthenticationDetails) {
                WebAuthenticationDetails authenticationDetails = (WebAuthenticationDetails) details;

                loginFailureBuilder.sourceIP(authenticationDetails.getRemoteAddress());
                log.info("Source IP Failure Event: {}", authenticationDetails.getRemoteAddress());
            }

            LoginFailure loginFailure = loginFailureRepository.save(loginFailureBuilder.build());

            if (loginFailure.getUser() != null) {
                lockAccount(loginFailure.getUser());
            }
            log.info("User locked");
        }
    }

    private void lockAccount(User user) {
        List<LoginFailure> failures =
                loginFailureRepository.findAllByUserAndCreatedAtIsAfter(user,
                        LocalDate.now().minusDays(1));

        if (failures.size() > 3) {
            log.info("Locking user account: {}", user);
            user.setAccountNonLocked(false);
            userRepository.save(user);

            throw new TooManyAuthenticationFailuresException(user.getUsername());
        }
    }
}
