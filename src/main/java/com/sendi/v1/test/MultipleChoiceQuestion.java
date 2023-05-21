package com.sendi.v1.test;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MultipleChoiceQuestion extends Question {
    private String actualAnswer;
    private String secondaryAnswer1;
    private String secondaryAnswer2;
    private String secondaryAnswer3;
}
