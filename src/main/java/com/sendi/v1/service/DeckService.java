package com.sendi.v1.service;

import com.sendi.v1.dto.DeckDTO;
import com.sendi.v1.security.domain.User;

import java.util.List;

public interface DeckService {
    List<DeckDTO> getDecksByUser(User user);

    List<DeckDTO> getDecksByUserId(Long userId);

    DeckDTO getDeckById(Long id);

    DeckDTO createOrUpdateDeck(DeckDTO deckDTO);

    void deleteById(Long id);
}
