package com.sendi.v1.dto.mapper;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.service.dto.FlashcardDTO;
import com.sendi.v1.service.dto.mapper.FlashcardMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlashcardMapperTest {
    FlashcardMapper flashcardMapper = FlashcardMapper.INSTANCE;

    @Test
    public void flashcardToFlashcardDTO() throws Exception {
        //given
        Deck deck = new Deck();
        deck.setName("deck1");
        deck.setDescription("desc1");

        Flashcard flashcard = new Flashcard();
        flashcard.setDeck(deck);
        flashcard.setTerm("term1");
        flashcard.setDefinition("definition1");

        //when
        FlashcardDTO flashcardDTO = flashcardMapper.toDTO(flashcard);

        //then
        assertEquals(flashcard.getTerm(), flashcardDTO.getTerm());
//        assertEquals(flashcard.getDeck(), flashcardDTO.getDeck());
        assertEquals(flashcard.getDefinition(), flashcardDTO.getDefinition());
    }

    @Test
    public void flashcardDTOToFlashcard() throws Exception {
        //given
        FlashcardDTO flashcardDTO = new FlashcardDTO();
        flashcardDTO.setTerm("term1");
        flashcardDTO.setDefinition("definition1");

        //when
        Flashcard flashcard = flashcardMapper.toEntity(flashcardDTO);

        //then
        assertEquals(flashcardDTO.getTerm(), flashcard.getTerm());
        assertEquals(flashcardDTO.getDefinition(), flashcard.getDefinition());
    }
}