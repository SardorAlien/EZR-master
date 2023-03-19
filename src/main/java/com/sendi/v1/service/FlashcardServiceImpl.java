package com.sendi.v1.service;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.dto.DeckDTO;
import com.sendi.v1.dto.FlashcardDTO;
import com.sendi.v1.dto.mapper.DeckMapper;
import com.sendi.v1.dto.mapper.FlashcardMapper;
import com.sendi.v1.repo.DeckRepository;
import com.sendi.v1.repo.FlashcardRepository;
import lombok.RequiredArgsConstructor;
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
public class FlashcardServiceImpl implements FlashcardService {
    private final FlashcardRepository flashcardRepo;
    private final DeckMapper deckMapper;
    private final FlashcardMapper flashcardMapper;
    private final DeckRepository deckRepo;

    @Override
    public List<FlashcardDTO> getFlashcardsByDeck(DeckDTO deckDTO) {
        if (deckDTO == null) {
            return Collections.emptyList();
        }

        Deck deck = deckMapper.deckDTOToDeck(deckDTO);

        List<Flashcard> flashcards = flashcardRepo.findAllByDeck(deck);

        List<FlashcardDTO> flashcardDTOList =
                flashcards.stream().map(flashcardMapper::flashcardToFlashcardDTO).collect(Collectors.toList());

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
                .map(flashcardMapper::flashcardToFlashcardDTO)
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
                .map(flashcardMapper::flashcardToFlashcardDTO)
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
                .map(flashcardMapper::flashcardToFlashcardDTO)
                .collect(Collectors.toList());

        return flashcardDTOList;
    }

    @Override
    public FlashcardDTO createOrUpdateFlashcard(Long deckId, FlashcardDTO flashcardDTO) {
        Optional<Deck> deckOptional = deckRepo.findById(deckId);

        if (deckOptional.isEmpty()) {
            throw new RuntimeException("Invalid deckId");
        }

        Deck deck = deckOptional.get();

        Flashcard flashcard = flashcardMapper.flashcardDTOToFlashcard(flashcardDTO);
        flashcard.setDeck(deck);

        flashcardRepo.save(flashcard);

        FlashcardDTO newFlashcardDTO = flashcardMapper.flashcardToFlashcardDTO(flashcard);

        return newFlashcardDTO;
    }

    @Override
    public FlashcardDTO getDeckById(Long flashcardId) {
        Optional<Flashcard> flashcardOptional = flashcardRepo.findById(flashcardId);

        if (flashcardOptional.isEmpty()) {
            return null;
        }

        Flashcard flashcard = flashcardOptional.get();

        FlashcardDTO newFlashcardDTO = flashcardMapper.flashcardToFlashcardDTO(flashcard);

        return newFlashcardDTO;
    }

    @Transactional
    @Override
    public void deleteById(Long flashcardId) {
        flashcardRepo.deleteFlashcardById(flashcardId);
    }
}
