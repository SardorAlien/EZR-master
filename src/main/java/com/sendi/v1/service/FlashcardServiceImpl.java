package com.sendi.v1.service;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.dto.DeckDTO;
import com.sendi.v1.dto.FlashcardDTO;
import com.sendi.v1.dto.mapper.DeckMapper;
import com.sendi.v1.dto.mapper.FlashcardMapper;
import com.sendi.v1.repo.FlashcardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    private final DeckService deckService;

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
        if (deckId < 0) {
            return Collections.emptyList();
        }

        DeckDTO deckDTO = deckService.getDeckById(deckId);

        return getFlashcardsByDeck(deckDTO);
    }

    @Override
    public FlashcardDTO createOrUpdateDeck(FlashcardDTO flashcardDTO) {
        Flashcard flashcard = flashcardMapper.flashcardDTOToFlashcard(flashcardDTO);

        flashcardRepo.save(flashcard);

        FlashcardDTO newFlashcardDTO = flashcardMapper.flashcardToFlashcardDTO(flashcard);

        return newFlashcardDTO;
    }

    @Override
    public void deleteById(Long flashcardId) {
        flashcardRepo.deleteById(flashcardId);
    }

    @Override
    public FlashcardDTO getDeckById(Long flashcardId) {
        Optional<Flashcard> flashcardOptional = flashcardRepo.getFlashcardById(flashcardId);

        if (flashcardOptional.isEmpty()) {
            return null;
        }

        Flashcard flashcard = flashcardOptional.get();

        FlashcardDTO newFlashcardDTO = flashcardMapper.flashcardToFlashcardDTO(flashcard);

        return newFlashcardDTO;
    }
}
