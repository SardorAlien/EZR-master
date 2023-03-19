package com.sendi.v1.dto.mapper;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.dto.DeckDTO;
import com.sendi.v1.dto.FlashcardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = FlashcardMapper.class)
public interface DeckMapper {
    DeckMapper INSTANCE = Mappers.getMapper(DeckMapper.class);

    @Mapping(source = "flashcardDTOs", target = "flashcards")
    Deck deckDTOToDeck(DeckDTO deckDTO);

    @Mapping(source = "flashcards", target = "flashcardDTOs")
    DeckDTO deckToDeckDTO(Deck deck);
}
