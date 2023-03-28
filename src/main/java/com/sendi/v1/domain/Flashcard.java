package com.sendi.v1.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "flashcards")
@EqualsAndHashCode(callSuper = true, exclude = "deck")
public class Flashcard extends BaseEntity {

    @Column(name = "term", nullable = false)
    private String term;

    @Column(name = "definition")
    private String definition;

    @ManyToOne(fetch = FetchType.LAZY)
    private Deck deck;

    @Column(name = "is_learned")
    @Builder.Default
    private boolean isLearned = false;
}
