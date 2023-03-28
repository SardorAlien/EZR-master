package com.sendi.v1.dto.mapper;

import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.dto.FlashcardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlashcardMapper {
    FlashcardMapper INSTANCE = Mappers.getMapper(FlashcardMapper.class);

    FlashcardDTO toDTO(Flashcard flashcard);

    Flashcard toEntity(FlashcardDTO flashcardDTO);

    List<FlashcardDTO> toFlashcardDTOs(List<Flashcard> flashcards);

    List<Flashcard> toFlashcards(List<FlashcardDTO> flashcardDTOs);
}
