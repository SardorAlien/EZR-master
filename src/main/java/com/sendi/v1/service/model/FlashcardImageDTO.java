package com.sendi.v1.service.model;

import com.fasterxml.jackson.annotation.*;
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

    @JsonIgnore
    private byte[] bytes;
}
