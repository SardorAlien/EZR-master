package com.sendi.v1.service;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.dto.DeckDTO;
import com.sendi.v1.dto.FlashcardDTO;
import com.sendi.v1.dto.mapper.DeckMapper;
import com.sendi.v1.dto.mapper.FlashcardMapper;
import com.sendi.v1.repo.DeckRepository;
import com.sendi.v1.repo.FlashcardRepository;
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
public class FlashcardServiceImpl implements FlashcardService {
    private final FlashcardRepository flashcardRepo;
    private final DeckMapper deckMapper;
    private final FlashcardMapper flashcardMapper;
    private final DeckRepository deckRepo;
    private final DeckService deckService;

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

    @Override
    public List<FlashcardDTO> getFlashcardsByDeck(DeckDTO deckDTO) {
        if (deckDTO == null) {
            return Collections.emptyList();
        }

        Deck deck = deckMapper.toEntity(deckDTO);

        List<Flashcard> flashcards = flashcardRepo.findAllByDeck(deck);

        List<FlashcardDTO> flashcardDTOList =
                flashcards.stream().map(flashcardMapper::toDTO).collect(Collectors.toList());

        return flashcardDTOList;
    }

    @Override
    public List<FlashcardDTO> getFlashcardsByDeckId(Long deckId) {
        Optional<Deck> deckOptional = deckRepo.findById(deckId);

        if (deckOptional.isEmpty()) {
            throw new RuntimeException("Invalid deckId");
        }

        Deck deck = deckOptional.get();

        List<FlashcardDTO> flashcardDTOList = flashcardRepo.findAllByDeck(deck)
                .stream()
                .map(flashcardMapper::toDTO)
                .collect(Collectors.toList());

        return flashcardDTOList;
    }

    @Override
    public List<FlashcardDTO> getFlashcardsByDeckId(Long deckId, Pageable pageable) {
        Optional<Deck> deckOptional = deckRepo.findById(deckId);

        if (deckOptional.isEmpty()) {
            throw new RuntimeException("Invalid deckId");
        }

        Deck deck = deckOptional.get();

        List<FlashcardDTO> flashcardDTOList = flashcardRepo.findAllByDeck(deck, pageable)
                .stream()
                .map(flashcardMapper::toDTO)
                .collect(Collectors.toList());

        return flashcardDTOList;
    }

    @Override
    public List<FlashcardDTO> getFlashcardsByDeckId(Long deckId, int page, int size) {
        Optional<Deck> deckOptional = deckRepo.findById(deckId);

        if (deckOptional.isEmpty()) {
            throw new RuntimeException("Invalid deckId");
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

        log.info("createOrUpdate | This is flashcardDTO => {}", flashcardDTO);

        Optional<Deck> deckOptional = deckRepo.findById(deckId);

        if (deckOptional.isEmpty()) {
            throw new RuntimeException("Invalid deckId");
        }

        Deck deck = deckOptional.get();

        Flashcard flashcard = flashcardMapper.toEntity(flashcardDTO);
        flashcard.setDeck(deck);

        log.info("createOrUpdate | This is flashcard => {}", flashcard);

        flashcardRepo.save(flashcard);

        FlashcardDTO newFlashcardDTO = flashcardMapper.toDTO(flashcard);

        return newFlashcardDTO;
    }

    @Transactional
    @Override
    public void deleteById(Long flashcardId) {
        flashcardRepo.deleteFlashcardById(flashcardId);
    }
}
