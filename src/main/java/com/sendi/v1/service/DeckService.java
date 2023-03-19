package com.sendi.v1.service;

import com.sendi.v1.dto.DeckDTO;
import com.sendi.v1.security.domain.User;

import java.util.List;

public interface DeckService {
    DeckDTO getDeckById(Long id);

    List<DeckDTO> getDecksByUser(User user);

    List<DeckDTO> getDecksByUserId(Long userId);

    DeckDTO createOrUpdateDeck(Long userId, DeckDTO deckDTO);

    void deleteById(Long deckId);
}
