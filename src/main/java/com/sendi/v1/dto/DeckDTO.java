package com.sendi.v1.dto;

import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.security.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeckDTO {
    private String name;
    private String description;
    private Set<Flashcard> flashcard;
    private User user;
}
