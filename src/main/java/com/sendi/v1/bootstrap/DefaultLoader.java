package com.sendi.v1.bootstrap;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.DeckVisibility;
import com.sendi.v1.security.domain.Authority;
import com.sendi.v1.security.domain.Role;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.service.AuthorityService;
import com.sendi.v1.security.service.RoleService;
import com.sendi.v1.security.service.UserService;
import com.sendi.v1.service.DeckService;
import com.sendi.v1.service.model.DeckDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class DefaultLoader implements CommandLineRunner {
    private final UserService userService;
    private final AuthorityService authorityService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final DeckService deckService;

    private void loadSecurityData() {
        try {
            log.info("Bootstrapping started...");
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
                        if (!authorityService.existsByPermission(deckAuthority.getPermission())) {
                            log.info("Saving authority: {}", deckAuthority.getPermission());
                            authorityService.createOrUpdate(deckAuthority);
                        }
                    });

            Arrays.asList(createFlashcard, readFlashcard, updateFlashcard, deleteFlashcard)
                    .stream()
                    .forEach(flashcardAuthority -> {
                        if (!authorityService.existsByPermission(flashcardAuthority.getPermission())) {
                            log.info("Saving authority: {}", flashcardAuthority.getPermission());
                            authorityService.createOrUpdate(flashcardAuthority);
                        }
                    });

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
                        if (!roleService.existsByRoleName(role.getName())) {
                            roleService.createOrUpdate(role);
                            log.info("Saving role: {}", role.getName());
                        }
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

            if (!userService.existsByUsername(user1.getUsername())) {
                log.info("Saving user: {}", user1.getUsername());
                userService.createOrUpdate(user1);
            }

            if (!userService.existsByUsername(user2.getUsername())) {
                log.info("Saving user: {}", user2.getUsername());
                userService.createOrUpdate(user2);
            }

        } catch (Exception e) {
            Arrays.stream(e.getStackTrace()).forEach(stackTraceElement -> log.error(String.valueOf(stackTraceElement)));
        } finally {
            log.info("Users loaded: {}", userService.count());
        }
    }

    private void loadDeckAndFlashcardData() {
        List<String> list = new ArrayList<>();
        List<DeckDTO> deckDTOs = new ArrayList<>();

        File file = new File("src/main/resources/static/unigram_freq.csv");
        log.info("File exists: {}", file.exists());

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            bufferedReader.lines().map(s -> s.substring(0, s.indexOf(','))).forEach(list::add);

            for (long i = 0; i < 1_000_000_000_000L; i++) {
                int randomNumber = (int) (Math.random() * (333334 - 1) + 1);
                int randomNumber2 = (int) (Math.random() * (333334 - 1) + 1);
                int randomNumForPercentage = (int) (Math.random() * 100);
                deckDTOs.add(DeckDTO.builder()
                                .name(list.get(randomNumber))
                                .description(list.get(randomNumber2))
                                .completionPercentage(randomNumForPercentage)
                                .createdAt(LocalDateTime.now())
                                .lastVisitedAt(LocalDateTime.now())
                                .deckVisibility(DeckVisibility.EVERYONE)
                        .build());

                if (deckDTOs.size() == 100_000) {
                    deckService.createOrUpdateAll(3L, deckDTOs);
                    deckDTOs.clear();
                    log.info("Done uploading hundred thousand rows of decks!");
                }
            }

            log.info("Done uploading billion rows of decks!");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            log.info("Decks loaded: {}", deckService.count());
        }
    }

    @Override
    public void run(String... args) throws Exception {
        loadSecurityData();
//        loadDeckAndFlashcardData();
    }


}