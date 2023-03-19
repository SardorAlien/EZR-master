package com.sendi.v1.dto.mapper;

import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.dto.FlashcardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface FlashcardMapper {
    FlashcardMapper INSTANCE = Mappers.getMapper(FlashcardMapper.class);

    FlashcardDTO flashcardToFlashcardDTO(Flashcard flashcard);

    Flashcard flashcardDTOToFlashcard(FlashcardDTO flashcardDTO);

    Set<FlashcardDTO> flashcardsToFlashcardDTOs(Set<Flashcard> flashcards);

    Set<Flashcard> flashcardDTOsToFlashcards(Set<FlashcardDTO> flashcardDTOs);
}
