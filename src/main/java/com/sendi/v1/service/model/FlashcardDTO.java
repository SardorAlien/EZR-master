package com.sendi.v1.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlashcardDTO implements FlashcardDTORepresentable {
    private Long id;
    private String term;
    private String definition;
    private boolean isLearned;
    private LocalDateTime createdAt;
}
