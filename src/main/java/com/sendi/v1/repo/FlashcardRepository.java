package com.sendi.v1.repo;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Flashcard;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FlashcardRepository extends CrudRepository<Flashcard, Long> {
    List<Flashcard> findAllByDeck(Deck deck);
    Optional<Flashcard> getFlashcardById(Long flashcardId);
//    List<Flashcard> findAllByDeck_Id(Long deckId);

}
