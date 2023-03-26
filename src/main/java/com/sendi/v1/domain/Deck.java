package com.sendi.v1.domain;

import com.sendi.v1.security.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "decks")
public class Deck extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "deck")
    private Set<Flashcard> flashcards;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @Column(name = "lastVisitedAt")
    private Timestamp lastVisitedAt;
}
