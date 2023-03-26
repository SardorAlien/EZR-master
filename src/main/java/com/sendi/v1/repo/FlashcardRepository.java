package com.sendi.v1.repo;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Flashcard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlashcardRepository extends PagingAndSortingRepository<Flashcard, Long> {
    List<Flashcard> findAllByDeck(Deck deck);
    List<Flashcard> findAllByDeck(Deck deck, Pageable pageable);
    void deleteFlashcardById(Long flashcardId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE flashcards f SET f.isLearned = :isLearned WHERE f.id in :flashcardIds AND f.deck.id = :deckId")
    void updateLearnedStateOfFlashcardsByDeckId(@Param("deckId") Long deckId,
                                                @Param("flashcardIds") List<Long> flashcardIds,
                                                @Param("isLearned") boolean isLearned);
}
