package com.sendi.v1.repo;

import com.sendi.v1.DefaultJpaTestConfiguration;
import com.sendi.v1.domain.Deck;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = DefaultJpaTestConfiguration.class)
class DeckRepositoryTest {

    @Autowired
    DeckRepository deckRepository;

    @Autowired
    UserRepository userRepository;

    private User user;
    private Deck deck1;
    private Deck deck2;

    @BeforeEach
    void setUp() {
        deck1 = new Deck();
        deck1.setName("deck1");
        deck1.setDescription("desc1");

        deck2 = new Deck();
        deck2.setName("deck2");
        deck2.setDescription("desc2");

        user = User.builder()
                .username("Sardor23")
                .email("sardor23@gmail.com")
                .firstname("Sardor")
                .lastname("Juma")
                .password("123")
                .build();

        deck1.setUser(user);
        deck2.setUser(user);
    }

    @Test
    void existsById() {
        userRepository.save(user);
        deckRepository.save(deck1);

        boolean doesExist = deckRepository.existsById(deck1.getId());

        assertTrue(doesExist);
    }
}