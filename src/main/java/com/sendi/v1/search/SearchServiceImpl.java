package com.sendi.v1.search;

import com.sendi.v1.repo.DeckRepository;
import com.sendi.v1.repo.FlashcardRepository;
import com.sendi.v1.security.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.sendi.v1.repo.DeckRepository.Specs.byDescription;
import static com.sendi.v1.repo.DeckRepository.Specs.byName;
import static com.sendi.v1.repo.FlashcardRepository.Specs.byDefinition;
import static com.sendi.v1.repo.FlashcardRepository.Specs.byTerm;
import static com.sendi.v1.security.repo.UserRepository.Specs.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class SearchServiceImpl implements SearchService {
    private final DeckRepository deckRepository;
    private final FlashcardRepository flashcardRepository;
    private final UserRepository userRepository;

    @Override
    public SearchResponse search(String query) {
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.getFoundDecks().addAll(deckRepository.findAll(byName(query)));
        searchResponse.getFoundDecks().addAll(deckRepository.findAll(byDescription(query)));

        log.info("This is found decks by {} ", deckRepository.findAll(byName(query)));
        log.info("This is found decks by {} ", deckRepository.findAll(byDescription(query)));

        searchResponse.getFoundFlashcards().addAll(flashcardRepository.findAll(byTerm(query)));
        searchResponse.getFoundFlashcards().addAll(flashcardRepository.findAll(byDefinition(query)));

        searchResponse.getFoundUsers().addAll(userRepository.findAll(byUsername(query)));
        searchResponse.getFoundUsers().addAll(userRepository.findAll(byFirstName(query)));
        searchResponse.getFoundUsers().addAll(userRepository.findAll(byLastName(query)));

        return searchResponse;
    }
}
