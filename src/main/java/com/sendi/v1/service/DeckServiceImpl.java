package com.sendi.v1.service;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.exception.custom.NoSuchDeckException;
import com.sendi.v1.exception.custom.NoSuchUserException;
import com.sendi.v1.service.dto.DeckDTO;
import com.sendi.v1.service.dto.mapper.DeckMapper;
import com.sendi.v1.service.dto.mapper.UserMapper;
import com.sendi.v1.repo.DeckRepository;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.repo.UserRepository;
import com.sendi.v1.security.service.UserService;
import com.sendi.v1.util.ErrorMessages;
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
        return getDecksByUser(user, Pageable.unpaged());
    }

    @Override
    public List<DeckDTO> getDecksByUser(User user, Pageable pageable) {
        if (user == null) {
            return Collections.emptyList();
        }

        if (pageable.isUnpaged()) {
            return deckRepo.findAllByUser(user)
                    .stream()
                    .map(deckMapper::toDTO)
                    .collect(Collectors.toList());
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
        if (page < 0) {
            return Collections.emptyList();
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        return getDecksByUser(user, pageRequest);
    }

    @Override
    public List<DeckDTO> getDecksByUserId(Long userId) {
        User user = Optional.ofNullable(userRepo.findById(userId))
                .orElseThrow(() -> new NoSuchUserException(userId))
                .get();

        return getDecksByUser(user);
    }

    @Override
    public List<DeckDTO> getDecksByUserId(Long userId, Pageable pageable) {
        User user = Optional.ofNullable(userRepo.findById(userId))
                .orElseThrow(() -> new NoSuchUserException(userId))
                .get();

        return getDecksByUser(user, pageable);
    }

    @Override
    public List<DeckDTO> getDecksByUserId(Long userId, int page, int size) {
        return getDecksByUserId(userId, PageRequest.of(page, size));
    }

    @Override
    public DeckDTO getOneById(Long id) {
        Deck deck = Optional.ofNullable(deckRepo.findById(id))
                .orElseThrow(() -> new NoSuchDeckException(id))
                .get();

        DeckDTO newDeckDTO = deckMapper.toDTO(deck);

        return newDeckDTO;
    }

    @Override
    @Transactional
    public DeckDTO createOrUpdate(Long userId, DeckDTO deckDTO) {
        User user = Optional.ofNullable(userRepo.findById(userId))
                .orElseThrow(() -> new NoSuchUserException(userId))
                .get();

        Deck deck = deckMapper.toEntity(deckDTO);
        deck.setUser(user);

        deckRepo.save(deck);

        DeckDTO newDeckDTO = deckMapper.toDTO(deck);

        return newDeckDTO;
    }

    @Override
    public void deleteById(Long id) {
        deckRepo.deleteById(id);
    }
}
