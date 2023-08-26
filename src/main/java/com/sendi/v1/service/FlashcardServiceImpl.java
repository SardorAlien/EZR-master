package com.sendi.v1.service;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.domain.Image;
import com.sendi.v1.exception.custom.NoSuchDeckException;
import com.sendi.v1.repo.ImageRepository;
import com.sendi.v1.service.model.DeckDTO;
import com.sendi.v1.service.model.FlashcardDTO;
import com.sendi.v1.service.model.FlashcardDTORepresentable;
import com.sendi.v1.service.model.FlashcardImageDTO;
import com.sendi.v1.service.model.mapper.DeckMapper;
import com.sendi.v1.service.model.mapper.FlashcardMapper;
import com.sendi.v1.repo.DeckRepository;
import com.sendi.v1.repo.FlashcardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    @Override
    public FlashcardDTORepresentable getOneById(Long flashcardId) {
        Optional<Flashcard> flashcardOptional = flashcardRepo.findById(flashcardId);

        if (flashcardOptional.isEmpty()) {
            throw new RuntimeException("Invalid flashcardId");
        }

        Flashcard flashcard = flashcardOptional.get();
        FlashcardDTO newFlashcardDTO = flashcardMapper.toDTO(flashcard);

        if (!Objects.isNull(flashcard.getImage())) {
            return FlashcardImageDTO.builder()
                    .bytes(flashcard.getImage().getData())
                    .flashcardDTO(newFlashcardDTO)
                    .build();
        }

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
    public List<FlashcardImageDTO> getFlashcardsByDeckId(Long deckId) {
        return getFlashcardsByDeckId(deckId, Pageable.unpaged());
    }

    @Transactional(readOnly = true)
    @Override
    public List<FlashcardImageDTO> getFlashcardsByDeckId(Long deckId, Pageable pageable) {
        if (!deckRepo.existsById(deckId)) {
            throw new NoSuchDeckException(deckId);
        }

        List<FlashcardImageDTO> flashcardDTOList = flashcardRepo.findAllByDeckId(deckId)
                .stream()
                .map(flashcard -> {
                    if (!Objects.isNull(flashcard.getImage()) && !Objects.isNull(flashcard.getImage().getData())) {
                        return FlashcardImageDTO.builder()
                                .bytes(flashcard.getImage().getData())
                                .flashcardDTO(flashcardMapper.toDTO(flashcard))
                                .build();
                    }

                    return FlashcardImageDTO.builder()
                            .bytes(null)
                            .flashcardDTO(flashcardMapper.toDTO(flashcard))
                            .build();
                })
                .collect(Collectors.toList());

        return flashcardDTOList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<FlashcardImageDTO> getFlashcardsByDeckId(Long deckId, int page, int size) {
        return getFlashcardsByDeckId(deckId, PageRequest.of(page, size));
    }

    @Override
    @Transactional
    public FlashcardImageDTO createOrUpdate(Long deckId,
                                            FlashcardDTO flashcardDTO,
                                            MultipartFile img
    ) throws IOException {
        Deck deck = Optional.ofNullable(deckRepo.findById(deckId))
                .orElseThrow(() -> new NoSuchDeckException(deckId))
                .get();

        Flashcard flashcard = flashcardMapper.toEntity(flashcardDTO);
        flashcard.setDeck(deck);
        Image image = Image.builder()
                .data(img.getBytes())
                .size(img.getSize())
                .name(img.getOriginalFilename())
                .flashcard(flashcard)
                .contentType(img.getContentType())
                .build();
        imageRepository.save(image);
        flashcardRepo.save(flashcard);

        FlashcardImageDTO flashcardImageDTO = new FlashcardImageDTO(flashcardDTO, img.getBytes());
        return flashcardImageDTO;
    }

    @Override
    public FlashcardDTO createOrUpdate(Long deckId, FlashcardDTO flashcardDTO) throws IOException {
        Deck deck = Optional.ofNullable(deckRepo.findById(deckId))
                .orElseThrow(() -> new NoSuchDeckException(deckId))
                .get();

        Flashcard flashcard = flashcardMapper.toEntity(flashcardDTO);
        flashcard.setDeck(deck);
        flashcardRepo.save(flashcard);

        FlashcardDTO savedFlashcardDTO = flashcardMapper.toDTO(flashcard);
        return savedFlashcardDTO;
    }

    @Transactional
    @Override
    public void deleteById(Long flashcardId) {
        flashcardRepo.deleteById(flashcardId);
    }
}
