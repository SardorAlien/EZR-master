package com.sendi.v1.dto;

import com.sendi.v1.domain.Deck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FlashcardDTO {
    private String term;
    private String definition;
    private Deck deck;
}
