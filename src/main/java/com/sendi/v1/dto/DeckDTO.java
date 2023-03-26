package com.sendi.v1.dto;

import lombok.*;

import java.sql.Timestamp;
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
    private Timestamp createdAt;
    private Timestamp lastVisitedAt;
}
