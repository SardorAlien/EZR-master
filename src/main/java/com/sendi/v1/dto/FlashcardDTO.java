package com.sendi.v1.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FlashcardDTO {
//    private Long id;
    private String term;
    private String definition;
    private boolean isLearned;
}
