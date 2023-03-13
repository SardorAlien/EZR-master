package com.sendi.v1.domain;

import lombok.*;

import javax.persistence.*;

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
}
