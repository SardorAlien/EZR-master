package com.sendi.v1.domain;

import com.sendi.v1.security.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "decks")
@EntityListeners(AuditingEntityListener.class)
public class Deck extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deck", fetch = FetchType.EAGER)
    private Set<Flashcard> flashcards;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @LastModifiedDate
    @Column(name = "last_visited_at")
    private LocalDateTime lastVisitedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Deck deck = (Deck) o;
        return getId() != null && Objects.equals(getId(), deck.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
