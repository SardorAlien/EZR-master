package com.sendi.v1.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;
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
    private LocalDateTime createdAt;
    private LocalDateTime lastVisitedAt;
}
