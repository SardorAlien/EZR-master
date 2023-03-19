package com.sendi.v1.dto;

import lombok.*;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeckDTO {
//    private Long id;
    private String name;
    private String description;
    private Set<FlashcardDTO> flashcardDTOs;
}
