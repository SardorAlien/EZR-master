package com.sendi.v1.repo;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Deck_;
import com.sendi.v1.security.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DeckRepository extends PagingAndSortingRepository<Deck, Long>,
        JpaSpecificationExecutor<Deck> {
    List<Deck> findAllByUser(User user);
    List<Deck> findAllByUser(User user, Pageable pageable);
    boolean existsById(Long id);
    List<Deck> findAllByUserId(long userId);
    List<Deck> findAllByUserId(long userId, Pageable pageable);

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
