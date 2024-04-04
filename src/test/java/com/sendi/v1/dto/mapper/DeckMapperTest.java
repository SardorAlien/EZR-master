package com.sendi.v1.dto.mapper;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.DeckVisibility;
import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.service.model.DeckDTO;
import com.sendi.v1.service.model.FlashcardDTO;
import com.sendi.v1.service.model.mapper.DeckMapper;
import com.sendi.v1.service.model.mapper.FlashcardMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class DeckMapperTest {
    @InjectMocks
    DeckMapper deckMapper = DeckMapper.INSTANCE;
    FlashcardMapper flashcardMapper = FlashcardMapper.INSTANCE;

    @BeforeEach
    public void init() {
        ReflectionTestUtils.setField(deckMapper, "flashcardMapper", flashcardMapper);
    }

    @Captor
    ArgumentCaptor<Set<Flashcard>> flashcardsArgumentCaptor;

    private class FlashcardMapperToDTO implements Answer<Set<FlashcardDTO>> {
        @Override
        public Set<FlashcardDTO> answer(InvocationOnMock invocationOnMock) throws Throwable {
            Set<Flashcard> flashcards = invocationOnMock.getArgument(0);
            log.info("flashcards captured {}", flashcards);
            if (flashcards.isEmpty()) return Collections.emptySet();

            return flashcards.stream().map(flashcard -> FlashcardDTO.builder()
                    .term(flashcard.getTerm())
                    .definition(flashcard.getDefinition())
                    .build()).collect(Collectors.toSet());
        }
    }

    @Test
    public void deckToDeckDTO() throws Exception {
        //given
        Deck deck = new Deck();
        deck.setName("deck1");
        deck.setDescription("desc1");

        Flashcard flashcard = new Flashcard();
        flashcard.setTerm("term1");
        flashcard.setDefinition("definition1");

        Set<Flashcard> flashcardSetExpected = Set.of(flashcard);
        deck.setFlashcards(flashcardSetExpected);
        deck.setDeckVisibility(DeckVisibility.EVERYONE);

        //whe
        DeckDTO deckDTO = deckMapper.toDTO(deck);

        //then
        assertEquals(deckDTO.getName(), deck.getName());
        assertEquals(deckDTO.getDescription(), deck.getDescription());
        assertEquals(deckDTO.getDeckVisibility(), DeckVisibility.EVERYONE);
    }

    @Test
    public void deckDTOToDeck() throws Exception {
        //given
        DeckDTO deckDTO = new DeckDTO();
        deckDTO.setName("deck1");
        deckDTO.setDescription("desc1");

        //when
        Deck deck = deckMapper.toEntity(deckDTO);

        //then
        assertEquals(deck.getName(), deckDTO.getName());
        assertEquals(deck.getDescription(), deckDTO.getDescription());
    }
}