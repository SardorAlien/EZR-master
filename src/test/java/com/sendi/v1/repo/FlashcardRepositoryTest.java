package com.sendi.v1.repo;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Flashcard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FlashcardRepositoryTest {

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Test
    void updateLearnedStateOfFlashcardsByDeckId() {
        //given
        Flashcard flashcard = new Flashcard();
        flashcard.setTerm("term1");
        flashcard.setDefinition("def1");
        flashcard.setDeck(new Deck());

        flashcardRepository.save(flashcard);

        List<Long> list = new ArrayList<>();
        list.add(1L);

        //when
//        flashcardRepository.updateLearnedStateOfFlashcardsByDeckId(1L, list, false);

        //then
        assertEquals(flashcardRepository.findById(1L).get().isLearned(), false);
    }
}