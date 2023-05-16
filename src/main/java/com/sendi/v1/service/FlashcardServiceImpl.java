package com.sendi.v1.service;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.exception.custom.NoSuchDeckException;
import com.sendi.v1.service.dto.DeckDTO;
import com.sendi.v1.service.dto.FlashcardDTO;
import com.sendi.v1.service.dto.mapper.DeckMapper;
import com.sendi.v1.service.dto.mapper.FlashcardMapper;
import com.sendi.v1.repo.DeckRepository;
import com.sendi.v1.repo.FlashcardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlashcardServiceImpl implements FlashcardService {
    private final FlashcardRepository flashcardRepo;
    private final DeckMapper deckMapper;
    private final FlashcardMapper flashcardMapper;
    private final DeckRepository deckRepo;

    @Transactional(readOnly = true)
    @Override
    public FlashcardDTO getOneById(Long flashcardId) {
        Optional<Flashcard> flashcardOptional = flashcardRepo.findById(flashcardId);

        if (flashcardOptional.isEmpty()) {
            throw new RuntimeException("Invalid flashcardId");
        }

        Flashcard flashcard = flashcardOptional.get();

        FlashcardDTO newFlashcardDTO = flashcardMapper.toDTO(flashcard);

        return newFlashcardDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FlashcardDTO> getFlashcardsByDeck(DeckDTO deckDTO) {
        if (deckDTO == null) {
            return Collections.emptyList();
        }

        Deck deck = deckMapper.toEntity(deckDTO);

        List<FlashcardDTO> flashcardDTOList = flashcardRepo.findAllByDeck(deck)
                .stream()
                .map(flashcardMapper::toDTO)
                .collect(Collectors.toList());

        log.info("flashcardDTOList: {}", flashcardDTOList);

        return flashcardDTOList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FlashcardDTO> getFlashcardsByDeckId(Long deckId) {
        Deck deck = Optional.ofNullable(deckRepo.findById(deckId))
                .orElseThrow(() -> new NoSuchDeckException(deckId))
                .get();

        List<FlashcardDTO> flashcardDTOList = flashcardRepo.findAllByDeck(deck)
                .stream()
                .map(flashcardMapper::toDTO)
                .collect(Collectors.toList());

        return flashcardDTOList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FlashcardDTO> getFlashcardsByDeckId(Long deckId, Pageable pageable) {
        Optional<Deck> deckOptional = deckRepo.findById(deckId);

        if (deckOptional.isEmpty()) {
            throw new NoSuchDeckException(deckId);
        }

        Deck deck = deckOptional.get();

        List<FlashcardDTO> flashcardDTOList = flashcardRepo.findAllByDeck(deck, pageable)
                .stream()
                .map(flashcardMapper::toDTO)
                .collect(Collectors.toList());

        return flashcardDTOList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FlashcardDTO> getFlashcardsByDeckId(Long deckId, int page, int size) {
        Optional<Deck> deckOptional = deckRepo.findById(deckId);

        if (deckOptional.isEmpty()) {
            throw new NoSuchDeckException(deckId);
        }

        Deck deck = deckOptional.get();

        List<FlashcardDTO> flashcardDTOList = flashcardRepo.findAllByDeck(deck, PageRequest.of(page, size))
                .stream()
                .map(flashcardMapper::toDTO)
                .collect(Collectors.toList());

        return flashcardDTOList;
    }

    @Override
    @Transactional
    public FlashcardDTO createOrUpdate(Long deckId, FlashcardDTO flashcardDTO) {
        Optional<Deck> deckOptional = deckRepo.findById(deckId);

        if (deckOptional.isEmpty()) {
            throw new NoSuchDeckException(deckId);
        }

        Deck deck = deckOptional.get();

        Flashcard flashcard = flashcardMapper.toEntity(flashcardDTO);
        flashcard.setDeck(deck);

        flashcardRepo.save(flashcard);

        FlashcardDTO newFlashcardDTO = flashcardMapper.toDTO(flashcard);

        return newFlashcardDTO;
    }

    @Transactional
    @Override
    public void deleteById(Long flashcardId) {
        flashcardRepo.deleteById(flashcardId);
    }
}
