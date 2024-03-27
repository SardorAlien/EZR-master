package com.sendi.v1.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sendi.v1.security.domain.User;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "decks")
@EntityListeners(AuditingEntityListener.class)
public class Deck extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deck", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonManagedReference
    private Set<Flashcard> flashcards;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonBackReference
    private User user;

    @Column(name = "last_visited_at")
    @CreatedDate
    private LocalDateTime lastVisitedAt;

    @Column(name = "completion_percentage")
    private Integer completionPercentage = 0;

    @Column(name = "visibility")
    private DeckVisibility deckVisibility;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deck", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonManagedReference
    private Set<DeckVisits> deckVisits;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
