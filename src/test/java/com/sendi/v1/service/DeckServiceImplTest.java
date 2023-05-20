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

import java.util.*;
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
    ArgumentCaptor<User> userCaptor;

    @Mock
    UserRepository userRepository;

    @Mock
    DeckMapper deckMapper;

    class FindDecks implements Answer<List<Deck>> {
        @Override
        public List<Deck> answer(InvocationOnMock invocationOnMock) throws Throwable {
            User user = invocationOnMock.getArgument(0);

            if (user == null)
                return Collections.emptyList();
            else {
                return sampleDecks();
            }
        }
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

    @Test
    void getDecksByUserExists() {
        when(deckRepository.findAllByUser(userCaptor.capture())).thenAnswer(new FindDecks());

        User actualUser = User.builder()
                .firstname("Harry")
                .lastname("Potter")
                .build();

        List<DeckDTO> actualDecks = service.getDecksByUser(actualUser);

        assertThat(userCaptor.getValue()).isNotNull();
        assertThat(actualDecks).hasSize(2);
    }

    @Test
    void getDecksByUserEmptyList() {
        lenient().when(deckRepository.findAllByUser(any(User.class))).thenAnswer(new FindDecks());

        User user = null;
        List<DeckDTO> decks = service.getDecksByUser(user);

        assertThat(decks).isEmpty();
        verify(deckRepository, never()).findAllByUser(any(User.class));
    }

    @Test
    void getDecksByUserId() {
        User expectedUser = User.builder()
                .firstname("Harry")
                .lastname("Potter")
                .build();
        expectedUser.setId(1L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(expectedUser));
        when(deckRepository.findAllByUser(any(User.class))).thenAnswer(new FindDecks());

        List<DeckDTO> actualDecks = service.getDecksByUserId(getRandomLong());

        assertThat(actualDecks).hasSize(2);

        verify(userRepository).findById(anyLong());
        verify(deckRepository).findAllByUser(any(User.class));
    }

    @Test
    void getOneById() {
        Deck expectedDeck = sampleDecks().get(0);
        System.out.println(expectedDeck);
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
}