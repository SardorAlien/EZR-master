package com.sendi.v1.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeckDTO {
    private Long id;
    private String name;
    private String description;
    private Set<FlashcardDTO> flashcardDTOS;
    private LocalDateTime createdAt;
    private LocalDateTime lastVisitedAt;

    @Builder.Default
    @Size(min = 0, max = 100, message = "Completion percenage should be between 0 and 100")
    private Integer completionPercentage = 0;

    public DeckDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public DeckDTO(Long id, String name, String description, LocalDateTime createdAt, LocalDateTime lastVisitedAt, Integer completionPercentage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.lastVisitedAt = lastVisitedAt;
        this.completionPercentage = completionPercentage;
    }
}
