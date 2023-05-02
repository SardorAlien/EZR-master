package com.sendi.v1.bootstrap;

import com.sendi.v1.exception.custom.UserDuplicationException;
import com.sendi.v1.security.domain.Authority;
import com.sendi.v1.security.domain.Role;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.repo.AuthorityRepository;
import com.sendi.v1.security.repo.RoleRepository;
import com.sendi.v1.security.repo.UserRepository;
import com.sendi.v1.security.service.AuthorityService;
import com.sendi.v1.security.service.RoleService;
import com.sendi.v1.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class DefaultLoader implements CommandLineRunner {
//    private final UserRepository userRepository;
    private final UserService userService;
    private final AuthorityService authorityService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    private void loadSecurityData() {
        try {
            Authority createDeck = Authority.builder().permission("deck.create").build();
            Authority readDeck = Authority.builder().permission("deck.read").build();
            Authority updateDeck = Authority.builder().permission("deck.update").build();
            Authority deleteDeck = Authority.builder().permission("deck.delete").build();

            Authority createFlashcard = Authority.builder().permission("flashcard.create").build();
            Authority readFlashcard = Authority.builder().permission("flashcard.read").build();
            Authority updateFlashcard = Authority.builder().permission("flashcard.update").build();
            Authority deleteFlashcard = Authority.builder().permission("flashcard.delete").build();

            Arrays.asList(createDeck, readDeck, updateDeck, deleteDeck)
                    .stream()
                    .forEach(deckAuthority -> {
                        authorityService.createOrUpdate(deckAuthority);
                    });

            Arrays.asList(createFlashcard, readFlashcard, updateFlashcard, deleteFlashcard)
                    .stream()
                    .forEach(flashcardAuthority -> {
                        authorityService.createOrUpdate(flashcardAuthority);
                    });;


            Role adminRole = Role.builder().name("ADMIN").build();
            Role customerRole = Role.builder().name("CUSTOMER").build();
            Role userRole = Role.builder().name("USER").build();

            adminRole.setAuthorities(new HashSet<>(Set.of(createDeck, readDeck, updateDeck, deleteDeck,
                    createFlashcard, readFlashcard, updateFlashcard, deleteFlashcard)));
            customerRole.setAuthorities(new HashSet<>(Set.of(createDeck, readDeck, updateDeck, deleteDeck,
                    createFlashcard, readFlashcard, updateFlashcard, deleteFlashcard)));
            userRole.setAuthorities(new HashSet<>(Set.of(createDeck, readDeck, updateDeck, deleteDeck,
                    createFlashcard, readFlashcard, updateFlashcard, deleteFlashcard)));

            Arrays.asList(adminRole, customerRole, userRole)
                    .stream()
                    .forEach(role -> {
                        roleService.createOrUpdate(role);
                    });

            User user1 = User.builder()
                    .username("sendilien")
                    .email("sardoralien@gmail.com")
                    .password(passwordEncoder.encode("5938240s"))
                    .firstname("Sendi")
                    .lastname("Alien")
                    .role(adminRole)
                    .build();

            User user2 = User.builder()
                    .username("sardoralien")
                    .email("sardorjumamuratov23@gmail.com")
                    .password(passwordEncoder.encode("sendi8240"))
                    .firstname("Sardor")
                    .lastname("Jumamuratov")
                    .role(adminRole)
                    .build();

            userService.createOrUpdate(user1);
            userService.createOrUpdate(user2);

        } catch (Exception e) {
            log.error(String.valueOf(e.getStackTrace()));
        } finally {
            log.info("Users loaded: {}", userService.count());
        }
    }

    @Override
    public void run(String... args) throws Exception {
        loadSecurityData();
    }
}
