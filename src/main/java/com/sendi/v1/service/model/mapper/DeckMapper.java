package com.sendi.v1.service.model.mapper;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.service.model.DeckDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = FlashcardMapper.class)
public interface DeckMapper {
    DeckMapper INSTANCE = Mappers.getMapper(DeckMapper.class);

    @Mapping(source = "flashcardDTOs", target = "flashcards")
    Deck toEntity(DeckDTO deckDTO);

    @Mapping(source = "flashcards", target = "flashcardDTOs")
    DeckDTO toDTO(Deck deck);
}
