package com.sendi.v1.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "flashcards")
public class Flashcard extends BaseEntity {

    @Column(name = "term", nullable = false)
    private String term;

    @Column(name = "definition")
    private String definition;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonBackReference
    private Deck deck;

    @Column(name = "is_learned")
    private Boolean isLearned = false;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
