package com.sendi.v1.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "flashcards")
public class Flashcard extends BaseEntity {

    @Column(name = "term")
    private String term;

    @Column(name = "definition")
    private String definition;

    @ManyToOne(fetch = FetchType.EAGER)
    private Deck deck;

    @Column(name = "is_learned")
    @Builder.Default
    private boolean isLearned = false;
}
