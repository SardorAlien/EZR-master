package com.sendi.v1.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RightOrWrongAnswer {
    private String question;
    private Object userAnswer;
    private long flashcardId;

    @JsonProperty(value = "isRight")
    private boolean isRight;
}
