package com.sendi.v1.service;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.repo.DeckRepository;
import com.sendi.v1.security.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeckServiceImpl implements DeckService {
    private final DeckRepository deckRepo;

    @Override
    public List<Deck> getDecksByUser(User user) {
        if (user == null) {
            return Collections.emptyList();
        }

        List<Deck> decks = deckRepo.findAllByUser(user);

        return decks;
    }
}
