package com.sendi.v1.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonPropertyOrder({"flashcard", "bytes"})
public class FlashcardImageDTO implements FlashcardDTORepresentable {
    @JsonProperty("flashcard")
    private FlashcardDTO flashcardDTO;
    private byte[] bytes;
}
