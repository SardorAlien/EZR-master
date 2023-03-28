package com.sendi.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlashcardDTO {
    private Long id;
    private String term;
    private String definition;
    private boolean isLearned;
    private Timestamp createdAt;
}
