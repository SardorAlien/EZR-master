package com.sendi.v1.search;

import com.sendi.v1.domain.Deck;
import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.security.domain.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    @Builder.Default
    private List<Deck> foundDecks = new ArrayList<>();
    @Builder.Default
    private List<User> foundUsers = new ArrayList<>();
    @Builder.Default
    private List<Flashcard> foundFlashcards = new ArrayList<>();
}
