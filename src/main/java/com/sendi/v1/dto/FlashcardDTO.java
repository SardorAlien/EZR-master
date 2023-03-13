package com.sendi.v1.dto;

import com.sendi.v1.domain.Deck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FlashcardDTO {
    private String term;
    private String definition;
    private Deck deck;
    private String isLearned;
}
