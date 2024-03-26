package com.sendi.v1.repo;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Deck_;
import com.sendi.v1.service.model.DeckDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DeckRepository extends PagingAndSortingRepository<Deck, Long>,
        JpaSpecificationExecutor<Deck> {
    boolean existsById(Long id);
    List<Deck> findAllByUserId(Long userId, Pageable pageable);

    @Query("select new com.sendi.v1.service.model.DeckDTO(d.id, d.name, d.description, " +
            "d.createdAt, d.lastVisitedAt, d.completionPercentage) " +
            "from decks d where d.user.id = :userId")
    Page<DeckDTO> findAllDecksWithoutFlashcardsByUserId(Long userId, Pageable pageable);

    @Query("select new com.sendi.v1.service.model.DeckDTO(d.id, d.name, d.description," +
            "d.createdAt, d.lastVisitedAt, d.completionPercentage) " +
            "from decks d where d.id = ?1")
    DeckDTO findDeckByIdWithoutFlashcards(Long id);

    @Modifying
    @Query(value = "update decks d set completion_percentage = " +
            "(select count(*) from flashcards f1 where is_learned = true and f1.deck_id = d.id) * 100 / " +
            "(select count(*) from flashcards f2 where f2.deck_id = d.id) from flashcards f " +
            "where d.id = :deckId and d.id = f.deck_id", nativeQuery = true)
    void updateCompletionPercentageByDeckId(Long deckId);


    @Modifying
    @Query(value = "update decks d set completion_percentage = " +
            "(select count(*) from flashcards f1, decks d1 where is_learned = true and d1.id = f1.deck_id and d1.user_id = :userId) * 100 /" +
            "(select count(*) from flashcards f2, decks d2 where d2.id = f2.deck_id and d2.user_id = :userId) " +
            "from flashcards as f where user_id = :userId and d.id = f.deck_id;", nativeQuery = true)
    void updateCompletionPercentageByUserId(Long userId);

    interface Specs {
        static Specification<Deck> byName(String name) {
            return (root, query, builder) ->
                    builder.equal(root.get(Deck_.name), name);
        }

        static Specification<Deck> byDescription(String description) {
            return (root, query, builder) ->
                    builder.equal(root.get(Deck_.description), description);
        }

        static Specification<Deck> orderByCreatedAt(
                Specification<Deck> spec) {
            return (root, query, builder) -> {
                query.orderBy(builder.asc(root.get(Deck_.createdAt)));
                return spec.toPredicate(root, query, builder);
            };
        }
    }
}
