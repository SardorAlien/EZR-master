package com.sendi.v1.repo;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeckRepository extends JpaRepository<Deck, Long> {
    List<Deck> findAllByUser(User user);
}
