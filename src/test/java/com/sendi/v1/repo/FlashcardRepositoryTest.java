package com.sendi.v1.repo;

import com.sendi.v1.DefaultJpaTestConfiguration;
import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Flashcard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ContextConfiguration(classes = DefaultJpaTestConfiguration.class)
class FlashcardRepositoryTest {

    @Autowired
    FlashcardRepository flashcardRepository;

    @Autowired
    DeckRepository deckRepository;

    private Deck deck;
    private Flashcard flashcard1;
    private Flashcard flashcard2;

    @BeforeEach
    void setUp() {
        deck = new Deck();
        deck.setName("deck1");
        deck.setDescription("desc1");

        flashcard1 = new Flashcard();
        flashcard1.setTerm("term 1");
        flashcard1.setDefinition("definition 1");
        flashcard1.setDeck(deck);

        flashcard2 = new Flashcard();
        flashcard2.setTerm("term 2");
        flashcard2.setDefinition("definition 2");
        flashcard2.setDeck(deck);
    }

    @Test
    void findAllByDeck() {
        deckRepository.save(deck);
        flashcardRepository.saveAll(List.of(flashcard1, flashcard2));

        List<Flashcard> flashcards = flashcardRepository.findAllByDeck(deck);

        assertThat(flashcards.get(0).getTerm()).isEqualTo(flashcard1.getTerm());
    }

    @Test
    void findAllByDeckPageable() {
        deckRepository.save(deck);

        Flashcard flashcard3 = new Flashcard();
        flashcard3.setTerm("term 3");
        flashcard3.setDefinition("definition 3");
        flashcard3.setDeck(deck);

        Flashcard flashcard4 = new Flashcard();
        flashcard4.setTerm("term 4");
        flashcard4.setDefinition("definition 4");
        flashcard4.setDeck(deck);

        flashcardRepository.saveAll(List.of(flashcard1, flashcard2, flashcard3, flashcard4));

        PageRequest pageRequest = PageRequest.of(1, 2);
        List<Flashcard> flashcards = flashcardRepository.findAllByDeck(deck, pageRequest);

        assertThat(flashcards.get(0).getTerm()).isEqualTo(flashcard3.getTerm());
    }
}