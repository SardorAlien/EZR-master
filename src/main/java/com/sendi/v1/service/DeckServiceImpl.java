package com.sendi.v1.service;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.dto.DeckDTO;
import com.sendi.v1.dto.mapper.DeckMapper;
import com.sendi.v1.dto.mapper.FlashcardMapper;
import com.sendi.v1.dto.mapper.UserMapper;
import com.sendi.v1.repo.DeckRepository;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.repo.UserRepository;
import com.sendi.v1.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeckServiceImpl implements DeckService {
    private final DeckRepository deckRepo;
    private final DeckMapper deckMapper;
    private final FlashcardMapper flashcardMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRepository userRepo;

    @Override
    public List<DeckDTO> getDecksByUser(User user) {
        if (user == null) {
            return Collections.emptyList();
        }

        List<DeckDTO> deckDTOs = deckRepo.findAllByUser(user)
                .stream()
                .map(deckMapper::deckToDeckDTO)
//                .map(deck -> deck.getFlashcards()
//                        .stream()
//                        .map(flashcardMapper::flashcardToFlashcardDTO)
//                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        log.info("This is deckDTOList {}", deckDTOs);

        return deckDTOs;
    }

    @Override
    public List<DeckDTO> getDecksByUserId(Long userId) {
        Optional<User> userOptional = userRepo.getUserById(userId);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid userId");
        }

        User user = userOptional.get();

        return getDecksByUser(user);
    }

    @Override
    public DeckDTO getDeckById(Long id) {
        Optional<Deck> deckOptional = deckRepo.findById(id);

        if (deckOptional.isEmpty()) {
            return null;
        }

        Deck deck = deckOptional.get();

        DeckDTO newDeckDTO = deckMapper.deckToDeckDTO(deck);

        return newDeckDTO;
    }

    @Override
    public DeckDTO createOrUpdateDeck(Long userId, DeckDTO deckDTO) {
        Optional<User> userOptional = userRepo.findById(userId);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid userId");
        }

        User user = userOptional.get();

        Deck deck = deckMapper.deckDTOToDeck(deckDTO);
        deck.setUser(user);

        deckRepo.save(deck);

        DeckDTO newDeckDTO = deckMapper.deckToDeckDTO(deck);

        return newDeckDTO;
    }

    @Override
    public void deleteById(Long id) {
        deckRepo.deleteById(id);
    }
}
