package com.sendi.v1.service;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.DeckVisibility;
import com.sendi.v1.exception.custom.NoSuchDeckException;
import com.sendi.v1.exception.custom.NoSuchUserException;
import com.sendi.v1.service.model.DeckDTO;
import com.sendi.v1.service.model.FlashcardDTO;
import com.sendi.v1.service.model.mapper.DeckMapper;
import com.sendi.v1.repo.DeckRepository;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
    @Transactional
    public List<DeckDTO> getDecksByUserId(Long userId) {
        return getDecksByUserId(userId, Pageable.unpaged());
    }

    @Transactional
    public List<DeckDTO> getDecksByUserId(Long userId, Pageable pageable) {
        updateCompletionPercentageByUserId(userId);

        List<DeckDTO> deckDTOList = deckRepo.findAllByUserId(userId, pageable)
                .stream()
                .map(deckMapper::toDTO)
                .collect(Collectors.toList());

        return deckDTOList;
    }

    @Override
    @Transactional
    public List<DeckDTO> getDecksByUserId(Long userId, int page, int size) {
        return getDecksByUserId(userId, PageRequest.of(page, size));
    }

    @Override
    @Transactional
    public List<DeckDTO> getDecksInfoByUserId(Long userId) {
        return getDecksInfoByUserId(userId, Pageable.unpaged());
    }

    @Transactional
    public List<DeckDTO> getDecksInfoByUserId(Long userId, Pageable pageable) {
        updateCompletionPercentageByUserId(userId);

        Page<DeckDTO> deckDTOPage = deckRepo.findAllDecksWithoutFlashcardsByUserId(userId, pageable);

        List<DeckDTO> deckDTOList = deckDTOPage.toList();


        return deckDTOList;
    }

    @Override
    @Transactional
    public List<DeckDTO> getDecksInfoByUserId(Long userId, int page, int size) {
        return getDecksInfoByUserId(userId, PageRequest.of(page, size));
    }

    @Override
    @Transactional
    public DeckDTO getOneById(Long deckId) {
        updateCompletionPercentageByDeckId(deckId);

        DeckDTO deckDTO = Optional.ofNullable(deckRepo.findById(deckId))
                .orElseThrow(() -> new NoSuchDeckException(deckId)).map(deckMapper::toDTO).
                get();


        return deckDTO;
    }

    @Override
    @Transactional
    public DeckDTO getOneByIdWithoutFlashcards(Long deckId) {
        updateCompletionPercentageByDeckId(deckId);

        DeckDTO deckDTO = Optional.ofNullable(deckRepo.findDeckByIdWithoutFlashcards(deckId))
                .orElseThrow(() -> new NoSuchDeckException(deckId));


        return deckDTO;
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
    @Transactional
    public void deleteById(Long id) {
        deckRepo.deleteById(id);
    }

    @Override
    public boolean existsById(Long deckId) {
        return deckRepo.existsById(deckId);
    }

    @Transactional
    public void updateCompletionPercentageByUserId(Long userId) {
        deckRepo.updateCompletionPercentageByUserId(userId);
    }

    @Transactional
    public void updateCompletionPercentageByDeckId(Long deckId) {
        deckRepo.updateCompletionPercentageByDeckId(deckId);
    }
}
