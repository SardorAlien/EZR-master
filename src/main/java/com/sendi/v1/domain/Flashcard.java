package com.sendi.v1.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "flashcards")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Flashcard flashcard = (Flashcard) o;
        return getId() != null && Objects.equals(getId(), flashcard.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
