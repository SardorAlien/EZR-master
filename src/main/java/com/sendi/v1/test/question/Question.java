package com.sendi.v1.test.question;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Question {
    protected String question;
    protected long flashcardId;
}
