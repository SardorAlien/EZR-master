package com.sendi.v1.security.authmanagers;

import com.sendi.v1.security.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@Slf4j
public class DeckAuthenticationManager {

    public boolean idMatchesDeck(Authentication authentication, Long userId) {
        User authenticatedUser = (User) authentication.getPrincipal();

        return authenticatedUser.getDecks()
                .stream()
                .map(deck -> deck.getUser().getId())
                .anyMatch(id -> Objects.equals(id, userId));
    }
}
