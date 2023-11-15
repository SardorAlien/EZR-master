package com.sendi.v1.service;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.exception.custom.NoSuchDeckException;
import com.sendi.v1.repo.DeckRepository;
import com.sendi.v1.repo.FlashcardRepository;
import com.sendi.v1.service.model.DeckDTO;
import com.sendi.v1.service.model.FlashcardDTO;
import com.sendi.v1.service.model.FlashcardDTORepresentable;
import com.sendi.v1.service.model.mapper.DeckMapper;
import com.sendi.v1.service.model.mapper.FlashcardMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.*;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlashcardServiceImplTest {

    @InjectMocks
    FlashcardServiceImpl service;

    @Mock
    FlashcardRepository flashcardRepository;

    @Mock
    FlashcardMapper flashcardMapper;

    @Mock
    DeckRepository deckRepository;

    @Mock
    DeckMapper deckMapper;

    @Captor
    ArgumentCaptor<Deck> deckCaptor;

    class FindFlashcards implements Answer<List<Flashcard>> {
        @Override
        public List<Flashcard> answer(InvocationOnMock invocationOnMock) throws Throwable {
            if (invocationOnMock.getArgument(0) instanceof Deck) {
                Deck deck = invocationOnMock.getArgument(0);
                if (deck == null) {
                    return Collections.emptyList();
                } else {
                    return sampleFlashcards();
                }
            } else if (invocationOnMock.getArgument(0) instanceof Long) {
                return sampleFlashcards();
            }

            throw new IllegalStateException("Invalid argument");
        }
    }

    private List<Flashcard> sampleFlashcards() {
        Flashcard flashcard1 = new Flashcard();
        flashcard1.setTerm("term1");
        flashcard1.setDefinition("def1");

        Flashcard flashcard2 = new Flashcard();
        flashcard2.setTerm("term2");
        flashcard2.setDefinition("def2");

        return List.of(flashcard1, flashcard2);
    }

    @Test
    void getFlashcardsByDeckExists() {
        when(flashcardRepository.findAllByDeck(deckCaptor.capture())).thenAnswer(new FindFlashcards());

        Deck expectedDeck = new Deck();
        expectedDeck.setName("deck1");
        expectedDeck.setDescription("desc1");
        when(deckMapper.toEntity(any(DeckDTO.class))).thenReturn(expectedDeck);

        DeckDTO actualDeckDTO = new DeckDTO();
        actualDeckDTO.setName(expectedDeck.getName());
        actualDeckDTO.setDescription(expectedDeck.getDescription());
        List<FlashcardDTO> actualFlashcardDTOS = service.getFlashcardsByDeck(actualDeckDTO);

        assertThat(deckCaptor.getValue()).isNotNull();
        assertThat(actualFlashcardDTOS).hasSize(2);

        verify(flashcardRepository).findAllByDeck(any(Deck.class));
        verify(deckMapper).toEntity(any(DeckDTO.class));
    }

    @Test
    void getFlashcardsByDeckEmpty() {
        lenient().when(flashcardRepository.findAllByDeck(any(Deck.class))).thenAnswer(new FindFlashcards());

        DeckDTO deckDTO = null;
        List<FlashcardDTO> actualFlashcardDTOS = service.getFlashcardsByDeck(deckDTO);

        assertThat(actualFlashcardDTOS).isEmpty();

        verify(flashcardRepository, never()).findAllByDeck(any(Deck.class));
    }

    @Test
    void getFlashcardsByDeckId() {
        when(deckRepository.existsById(1L)).thenReturn(true);
        when(flashcardRepository.findAllByDeckId(1L)).thenAnswer(new FindFlashcards());

//        List<FlashcardDTO> actualFlashcardDTOS = service.getFlashcardsByDeckId(1L);

//        assertThat(actualFlashcardDTOS).hasSize(2);

        verify(deckRepository).existsById(anyLong());
        verify(flashcardRepository).findAllByDeckId(anyLong());
    }

    @Test
    void getFlashcardsByDeckIdThrowNoSuchDeckException() {
        when(deckRepository.existsById(anyLong())).thenThrow(NoSuchDeckException.class);

        assertThrows(NoSuchDeckException.class, () -> {
            service.getFlashcardsByDeckId(anyLong());
        });

        verify(deckRepository).existsById(anyLong());
        verify(flashcardRepository, never()).findAllByDeck(any(Deck.class));
    }

    @Test
    void createOrUpdate() throws IOException {
        Flashcard expectedFlashcard = sampleFlashcards().get(0);
        when(flashcardRepository.save(any(Flashcard.class))).thenReturn(expectedFlashcard);
        when(deckRepository.findById(anyLong())).thenReturn(Optional.of(new Deck()));

        FlashcardDTO expectedFlashcardDTO = new FlashcardDTO();
        expectedFlashcardDTO.setTerm(expectedFlashcard.getTerm());
        expectedFlashcardDTO.setDefinition(expectedFlashcard.getDefinition());

        when(flashcardMapper.toDTO(any(Flashcard.class))).thenReturn(expectedFlashcardDTO);
        when(flashcardMapper.toEntity(any(FlashcardDTO.class))).thenReturn(expectedFlashcard);

        FlashcardDTO actualFlashcardDTO = service.createOrUpdate(anyLong(), new ArrayList<>(Arrays.asList(new FlashcardDTO()))).get(0);

        assertThat(actualFlashcardDTO.getTerm()).isEqualTo(expectedFlashcardDTO.getTerm());
        assertThat(actualFlashcardDTO.getDefinition()).isEqualTo(expectedFlashcardDTO.getDefinition());
    }

    @Test
    void createOrUpdateThrowNoSuchDeckException() {
        when(deckRepository.findById(anyLong())).thenThrow(NoSuchDeckException.class);

        assertThrows(NoSuchDeckException.class, () -> {
            service.createOrUpdate(getRandomLong(), new ArrayList<>(Arrays.asList(new FlashcardDTO())));
        });
    }

    @Test
    void getOneById() {
        Flashcard expectedFlashcard = sampleFlashcards().get(0);
        when(flashcardRepository.findById(anyLong())).thenReturn(Optional.ofNullable(expectedFlashcard));

        FlashcardDTO flashcardDTO = new FlashcardDTO();
        flashcardDTO.setTerm(expectedFlashcard.getTerm());
        flashcardDTO.setDefinition(expectedFlashcard.getDefinition());
        when(flashcardMapper.toDTO(any(Flashcard.class))).thenReturn(flashcardDTO);

        FlashcardDTORepresentable actualFlashcardDTO = service.getOneById(getRandomLong());

//        assertThat(actualFlashcardDTO.getTerm()).isEqualTo(expectedFlashcard.getTerm());
//        assertThat(actualFlashcardDTO.getDefinition()).isEqualTo(expectedFlashcard.getDefinition());

        verify(flashcardRepository).findById(anyLong());
        verify(flashcardMapper).toDTO(any(Flashcard.class));
    }

    @Test
    void deleteById() {
        doNothing().when(flashcardRepository).deleteById(anyLong());

        service.deleteById(getRandomLong());

        verify(flashcardRepository).deleteById(anyLong());
    }

    private long getRandomLong() {
        return new Random().longs(1, 10).findFirst().getAsLong();
    }

}