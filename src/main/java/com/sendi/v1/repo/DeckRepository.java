package com.sendi.v1.repo;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.security.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DeckRepository extends PagingAndSortingRepository<Deck, Long> {
    List<Deck> findAllByUser(User user);
    List<Deck> findAllByUser(User user, Pageable pageable);
}
