package com.sendi.v1.service;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.dto.DeckDTO;
import com.sendi.v1.dto.mapper.DeckMapper;
import com.sendi.v1.repo.DeckRepository;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private final UserRepository userRepo;

    @Override
    public List<DeckDTO> getDecksByUser(User user) {
        if (user == null) {
            return Collections.emptyList();
        }

        List<DeckDTO> deckDTOs = deckRepo.findAllByUser(user)
                .stream()
                .map(deckMapper::toDTO)
                .collect(Collectors.toList());

        log.info("This is deckDTOList {}", deckDTOs);

        return deckDTOs;
    }

    @Override
    public List<DeckDTO> getDecksByUser(User user, Pageable pageable) {
        if (user == null) {
            return Collections.emptyList();
        }

        List<DeckDTO> deckDTOs = deckRepo.findAllByUser(user, pageable)
                .stream()
                .map(deckMapper::toDTO)
                .collect(Collectors.toList());

        log.info("This is deckDTOList {}", deckDTOs);

        return deckDTOs;
    }

    @Override
    public List<DeckDTO> getDecksByUser(User user, int page, int size) {
        return null;
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
    public List<DeckDTO> getDecksByUserId(Long userId, Pageable pageable) {
        Optional<User> userOptional = userRepo.getUserById(userId);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid userId");
        }

        User user = userOptional.get();

        return getDecksByUser(user, pageable);
    }

    @Override
    public List<DeckDTO> getDecksByUserId(Long userId, int page, int size) {
        Optional<User> userOptional = userRepo.getUserById(userId);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid userId");
        }

        User user = userOptional.get();

        return getDecksByUser(user, PageRequest.of(page, size));
    }

    @Override
    public DeckDTO getOneById(Long id) {
        Optional<Deck> deckOptional = deckRepo.findById(id);

        if (deckOptional.isEmpty()) {
            throw new RuntimeException("Invalid deckId");
        }

        Deck deck = deckOptional.get();

        DeckDTO newDeckDTO = deckMapper.toDTO(deck);

        return newDeckDTO;
    }

    @Override
    @Transactional
    public DeckDTO createOrUpdate(Long userId, DeckDTO deckDTO) {

        log.info("createOrUpdate | This is deckDTO => {}", deckDTO);

        Optional<User> userOptional = userRepo.findById(userId);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid userId");
        }

        User user = userOptional.get();

        Deck deck = deckMapper.toEntity(deckDTO);
        deck.setUser(user);

        log.info("createOrUpdate | This is deck => {}", deck);

        deckRepo.save(deck);

        DeckDTO newDeckDTO = deckMapper.toDTO(deck);

        return newDeckDTO;
    }

    @Override
    public void deleteById(Long id) {
        deckRepo.deleteById(id);
    }
}
