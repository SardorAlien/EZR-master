package com.sendi.v1.service.dto.mapper;

import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.service.dto.FlashcardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface FlashcardMapper {
    FlashcardMapper INSTANCE = Mappers.getMapper(FlashcardMapper.class);

    FlashcardDTO toDTO(Flashcard flashcard);

    Flashcard toEntity(FlashcardDTO flashcardDTO);

    Set<FlashcardDTO> toDTOs(Set<Flashcard> flashcards);

    Set<Flashcard> toEntities(Set<FlashcardDTO> flashcardDTOs);
}
