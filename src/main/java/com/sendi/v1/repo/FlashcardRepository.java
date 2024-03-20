package com.sendi.v1.repo;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.domain.Flashcard_;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FlashcardRepository extends PagingAndSortingRepository<Flashcard, Long>,
        JpaSpecificationExecutor<Flashcard> {
    List<Flashcard> findAllByDeck(Deck deck);
    List<Flashcard> findAllByDeck(Deck deck, Pageable pageable);
    List<Flashcard> findAllByDeckId(Long deckId);
    List<Flashcard> findAllByDeckId(Long deckId, Pageable pageable);

//    @Modifying(flushAutomatically = true, clearAutomatically = true)
//    @Query(value = "UPDATE flashcards f SET f.isLearned = :isLearned WHERE f.id in :flashcardIds AND f.deck.id = :deckId")
//    void updateLearnedStateOfFlashcardsByDeckId(@Param("deckId") Long deckId,
//                                                @Param("flashcardIds") List<Long> flashcardIds,
//                                                @Param("isLearned") boolean isLearned);

    interface Specs {
        static Specification<Flashcard> byTerm(String term) {
            return (root, query, builder) ->
                    builder.equal(root.get(Flashcard_.term), term);
        }

        static Specification<Flashcard> byDefinition(String definition) {
            return (root, query, builder) ->
                    builder.equal(root.get(Flashcard_.definition), definition);
        }

        static Specification<Flashcard> orderByCreatedAt(
                Specification<Flashcard> spec) {
            return (root, query, builder) -> {
                query.orderBy(builder.asc(root.get(Flashcard_.createdAt)));
                return spec.toPredicate(root, query, builder);
            };
        }
    }
}
