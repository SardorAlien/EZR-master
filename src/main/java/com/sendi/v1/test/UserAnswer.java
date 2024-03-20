package com.sendi.v1.test;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserAnswer {
    private String question;
    private Object answer;
    private long flashcardId;
}
