package com.sendi.v1.repo;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Deck_;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.service.model.DeckDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DeckRepository extends PagingAndSortingRepository<Deck, Long>,
        JpaSpecificationExecutor<Deck> {
    boolean existsById(Long id);
    List<Deck> findAllByUserId(Long userId);
    List<Deck> findAllByUserId(Long userId, Pageable pageable);

    @Query("select new com.sendi.v1.service.model.DeckDTO(d.id, d.name, d.description, " +
            "d.createdAt, d.lastVisitedAt, d.completionPercentage) " +
            "from decks d where d.user.id = :userId")
    Page<DeckDTO> findAllDecksWithoutFlashcardsByUserId(Long userId, Pageable pageable);

    @Query("select new com.sendi.v1.service.model.DeckDTO(d.id, d.name, d.description," +
            "d.createdAt, d.lastVisitedAt, d.completionPercentage) " +
            "from decks d where d.id = ?1")
    DeckDTO findDeckByIdWithoutFlashcards(Long id);

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
