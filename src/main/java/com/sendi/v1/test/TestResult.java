package com.sendi.v1.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestResult {
    private int totalQuestions;
    private int rightAnswersCount;
    private int wrongAnswersCount;
    private int percentage;
}
