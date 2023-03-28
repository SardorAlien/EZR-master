package com.sendi.v1.dto.mapper;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.dto.DeckDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckMapperTest {
    DeckMapper deckMapper = DeckMapper.INSTANCE;

    @Test
    public void deckToDeckDTO() throws Exception {
        //given
        Deck deck = new Deck();
        deck.setName("deck1");
        deck.setDescription("desc1");

        //when
        DeckDTO deckDTO = deckMapper.toDTO(deck);

        //then
        assertEquals(deckDTO.getName(), deck.getName());
        assertEquals(deckDTO.getDescription(), deck.getDescription());
    }

    @Test
    public void deckDTOToDeck() throws Exception {
        //given
        DeckDTO deckDTO = new DeckDTO();
        deckDTO.setName("deck1");
        deckDTO.setDescription("desc1");

        //when
        Deck deck = deckMapper.toEntity(deckDTO);

        //then
        assertEquals(deck.getName(), deckDTO.getName());
        assertEquals(deck.getDescription(), deckDTO.getDescription());
    }
}