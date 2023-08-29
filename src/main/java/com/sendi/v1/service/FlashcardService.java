package com.sendi.v1.service;

import com.sendi.v1.service.model.DeckDTO;
import com.sendi.v1.service.model.FlashcardDTO;
import com.sendi.v1.service.model.FlashcardDTORepresentable;
import com.sendi.v1.service.model.FlashcardImageDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FlashcardService {
    FlashcardDTORepresentable getOneById(Long flashcardId);

    List<FlashcardDTO> getFlashcardsByDeck(DeckDTO deck);

    List<FlashcardImageDTO> getFlashcardsByDeckId(Long deckId);

    List<FlashcardImageDTO> getFlashcardsByDeckId(Long deckId, Pageable pageable);

    List<FlashcardImageDTO> getFlashcardsByDeckId(Long deckId, int page, int size);

//    List<FlashcardImageDTO> createOrUpdate(Long deckId, Map<FlashcardDTO, MultipartFile> flashcardDTOMultipartFileMap) throws IOException;

    List<FlashcardDTO> createOrUpdate(Long deckId, List<FlashcardDTO> flashcardDTO) throws IOException;

    Object createOrUpdate(Long deckId, List<FlashcardDTO> flashcardDTOList, HttpServletRequest httpServletRequest) throws IOException;

    void deleteById(Long flashcardId);
}
