package com.sendi.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.sql.Timestamp;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeckDTO {
    private Long id;
    private String name;
    private String description;
    private Set<FlashcardDTO> flashcardDTOs;
    private Timestamp createdAt;
    private Timestamp lastVisitedAt;
}
