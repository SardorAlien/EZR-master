package com.sendi.v1.domain;

import com.sendi.v1.security.domain.User;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "decks")
@EqualsAndHashCode(callSuper = true, exclude = "flashcards")
@EntityListeners(AuditingEntityListener.class)
public class Deck extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "deck")
    private Set<Flashcard> flashcards;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @LastModifiedDate
    @Column(name = "last_visited_at")
    private Timestamp lastVisitedAt;
}
