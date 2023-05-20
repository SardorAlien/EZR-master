package com.sendi.v1.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MultipleChoiceQuestion extends Question {
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
}
