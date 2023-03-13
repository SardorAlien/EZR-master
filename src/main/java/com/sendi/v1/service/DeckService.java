package com.sendi.v1.service;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.security.domain.User;

import java.util.List;
import java.util.Optional;

public interface DeckService {
    List<Deck> getDecksByUser(User user);
}
