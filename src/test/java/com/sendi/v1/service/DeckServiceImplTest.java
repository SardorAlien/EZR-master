package com.sendi.v1.service;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.exception.custom.NoSuchDeckException;
import com.sendi.v1.exception.custom.NoSuchUserException;
import com.sendi.v1.repo.DeckRepository;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.repo.UserRepository;
import com.sendi.v1.service.model.DeckDTO;
import com.sendi.v1.service.model.mapper.DeckMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeckServiceImplTest {

    @InjectMocks
    DeckServiceImpl service;

    @Mock
    DeckRepository deckRepository;

    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;

    @Captor
    ArgumentCaptor<Deck> deckArgumentCaptor;

    @Captor
    ArgumentCaptor<DeckDTO> deckDTOArgumentCaptor;

    @Mock
    UserRepository userRepository;

    @Mock
    DeckMapper deckMapper;

    private class FindDecksWithUserId implements Answer<List<Deck>> {
        @Override
        public List<Deck> answer(InvocationOnMock invocationOnMock) throws Throwable {
            Long userId = invocationOnMock.getArgument(0);

            if (userId > 0) return sampleDecks();

            return Collections.emptyList();
        }
    }

    private class DeckMapperToDTO implements Answer<DeckDTO> {
        @Override
        public DeckDTO answer(InvocationOnMock invocationOnMock) throws Throwable {
            if (invocationOnMock.getArgument(0) instanceof Deck) {
                Deck deck = invocationOnMock.getArgument(0);
                DeckDTO mappedDeck = DeckDTO.builder()
                        .name(deck.getName())
                        .description(deck.getDescription())
                        .build();

                return mappedDeck;
            }

            return null;
        }
    }

    private class DeckDTOMapperToEntity implements Answer<Deck> {
        @Override
        public Deck answer(InvocationOnMock invocationOnMock) throws Throwable {
            if (invocationOnMock.getArgument(0) instanceof DeckDTO) {
                DeckDTO deckDTO = invocationOnMock.getArgument(0);
                Deck mappedDeck = new Deck();
                mappedDeck.setName(deckDTO.getName());
                mappedDeck.setDescription(deckDTO.getDescription());

                return mappedDeck;
            }

            return null;
        }
    }

    @Test
    void getDecksByUserExists() {
        when(deckRepository.findAllByUserId(longArgumentCaptor.capture(), any())).thenAnswer(new FindDecksWithUserId());

        List<DeckDTO> actualDecks = service.getDecksByUserId(getRandomLong());

        assertThat(longArgumentCaptor.getValue()).isNotNull();
        assertThat(actualDecks).hasSize(2);
    }


    @Test
    void getDecksByUserId() {
        when(deckRepository.findAllByUserId(anyLong(), any())).thenAnswer(new FindDecksWithUserId());

        List<DeckDTO> actualDecks = service.getDecksByUserId(getRandomLong());

        assertThat(actualDecks).hasSize(2);
        verify(deckRepository).findAllByUserId(anyLong(), any());
    }

    @Test
    void getOneById() {
        Deck expectedDeck = sampleDecks().get(0);
        when(deckRepository.findById(anyLong())).thenReturn(Optional.ofNullable(expectedDeck));

        DeckDTO expectedDeckDTO = new DeckDTO();
        expectedDeckDTO.setName(expectedDeck.getName());
        expectedDeckDTO.setDescription(expectedDeck.getDescription());

        when(deckMapper.toDTO(any(Deck.class))).thenReturn(expectedDeckDTO);

        DeckDTO actualDeckDTO = service.getOneById(getRandomLong());

        assertThat(actualDeckDTO.getName()).isEqualTo(expectedDeckDTO.getName());
        assertThat(actualDeckDTO.getDescription()).isEqualTo(expectedDeckDTO.getDescription());

        verify(deckRepository).findById(anyLong());
        verify(deckMapper).toDTO(any(Deck.class));
    }

    @Test
    void getOneByIdShouldThrowNoSuchDeckException() {
        when(deckRepository.findById(anyLong())).thenThrow(NoSuchDeckException.class);

        assertThrows(NoSuchDeckException.class, () -> {
            service.getOneById(getRandomLong());
        });

        verify(deckRepository).findById(anyLong());
    }

    @Test
    void getOneByIdWithoutFlashcards() {
        Deck deck = sampleDecks().get(0);
        DeckDTO expectedDeckDTO = DeckDTO.builder()
                .name(deck.getName())
                .description(deck.getDescription())
                .build();

        when(deckRepository.findDeckByIdWithoutFlashcards(anyLong())).thenReturn(expectedDeckDTO);

        DeckDTO actualDeckDTO = service.getOneByIdWithoutFlashcards(getRandomLong());

        assertThat(actualDeckDTO.getName()).isEqualTo(expectedDeckDTO.getName());
        assertThat(actualDeckDTO.getFlashcardDTOS()).isNull();

        verify(deckRepository).findDeckByIdWithoutFlashcards(anyLong());
    }

    @Test
    void createOrUpdate() {
        Deck expectedDeck = sampleDecks().get(0);
        when(deckRepository.save(any(Deck.class))).thenReturn(expectedDeck);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));

        DeckDTO expectedDeckDTO = new DeckDTO();
        expectedDeckDTO.setName(expectedDeck.getName());
        expectedDeckDTO.setDescription(expectedDeck.getDescription());

        when(deckMapper.toDTO(any(Deck.class))).thenReturn(expectedDeckDTO);
        when(deckMapper.toEntity(any(DeckDTO.class))).thenReturn(expectedDeck);

        DeckDTO actualDeckDTO = service.createOrUpdate(getRandomLong(), new DeckDTO());

        assertThat(actualDeckDTO.getName()).isEqualTo(expectedDeckDTO.getName());
        assertThat(actualDeckDTO.getDescription()).isEqualTo(expectedDeckDTO.getDescription());

        verify(deckMapper, times(1)).toEntity(any(DeckDTO.class));
        verify(deckMapper, times(1)).toDTO(any(Deck.class));
    }

    @Test
    void createOrUpdateAll() {
        Iterable<Deck> expectedDeckIterable = sampleDecks();
        when(deckRepository.saveAll(anyList())).thenReturn(expectedDeckIterable);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));

        Iterator<Deck> decksIterator = expectedDeckIterable.iterator();

        List<Deck> expectedDeckList = new ArrayList<>();
        decksIterator.forEachRemaining(expectedDeckList::add);

        List<DeckDTO> expectedDeckDTOList = expectedDeckList.stream().map(deck -> DeckDTO.builder()
                .name(deck.getName())
                .description(deck.getDescription())
                .build()).collect(Collectors.toList());

        when(deckMapper.toDTO(deckArgumentCaptor.capture())).thenAnswer(new DeckMapperToDTO());
        when(deckMapper.toEntity(deckDTOArgumentCaptor.capture())).thenAnswer(new DeckDTOMapperToEntity());

        List<DeckDTO> deckDTOListToBeSaved = expectedDeckDTOList;
        Iterable<DeckDTO> actualSavedDeckDTOList = service.createOrUpdateAll(getRandomLong(), deckDTOListToBeSaved);
        Iterator<DeckDTO> actualDeckDTOsIterator = actualSavedDeckDTOList.iterator();

        List<DeckDTO> actualDeckDTOList = new ArrayList<>();
        actualDeckDTOsIterator.forEachRemaining(actualDeckDTOList::add);

        List<Deck> actualDeckList = actualDeckDTOList.stream().map(deckDTO -> {
            Deck deck = new Deck();
            deck.setName(deckDTO.getName());
            deck.setDescription(deckDTO.getDescription());
            return deck;
        }).collect(Collectors.toList());

        assertThat(actualDeckList.get(0).getName()).isEqualTo(expectedDeckList.get(0).getName());
        assertThat(actualDeckList.size()).isEqualTo(expectedDeckList.size());

        verify(deckMapper, times(2)).toEntity(any(DeckDTO.class));
        verify(deckMapper, times(2)).toDTO(any(Deck.class));
    }

    @Test
    void createOrUpdateThrowNoSuchUserException() {
        when(userRepository.findById(anyLong())).thenThrow(NoSuchUserException.class);

        assertThrows(NoSuchUserException.class, () -> {
            service.createOrUpdate(getRandomLong(), new DeckDTO());
        });

        verify(userRepository).findById(anyLong());
        verify(deckRepository, never()).save(any(Deck.class));
    }

    @Test
    void deleteById() {
        doNothing().when(deckRepository).deleteById(anyLong());

        service.deleteById(getRandomLong());

        verify(deckRepository).deleteById(anyLong());
    }

    private long getRandomLong() {
        return new Random().longs(1, 10).findFirst().getAsLong();
    }

    private List<Deck> sampleDecks() {
        Deck deck1 = new Deck();
        deck1.setName("deck1");
        deck1.setDescription("desc1");

        Deck deck2 = new Deck();
        deck2.setName("deck2");
        deck2.setDescription("desc2");
        return List.of(deck1, deck2);
    }
}