package com.sendi.v1.service.model.mapper;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.service.model.DeckDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = FlashcardMapper.class)
public interface DeckMapper {
    DeckMapper INSTANCE = Mappers.getMapper(DeckMapper.class);

    @Mapping(source = "flashcardDTOS", target = "flashcards")
    @ValueMapping(source = "EVERYONE", target = "EVERYONE")
    @ValueMapping(source = "ME", target = "ME")
    @ValueMapping(source = "WITH_PASSWORD", target = "WITH_PASSWORD")
    Deck toEntity(DeckDTO deckDTO);


    @Mapping(source = "flashcards", target = "flashcardDTOS")
    @ValueMapping(source = "EVERYONE", target = "EVERYONE")
    @ValueMapping(source = "ME", target = "ME")
    @ValueMapping(source = "WITH_PASSWORD", target = "WITH_PASSWORD")
    DeckDTO toDTO(Deck deck);
}
