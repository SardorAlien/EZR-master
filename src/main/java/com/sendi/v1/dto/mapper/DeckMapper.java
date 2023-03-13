package com.sendi.v1.dto.mapper;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.dto.DeckDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeckMapper {
    DeckMapper INSTANCE = Mappers.getMapper(DeckMapper.class);

    Deck deckDTOToDeck(DeckDTO deckDTO);
    DeckDTO deckToDeckDTO(Deck deck);
}
