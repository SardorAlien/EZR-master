package com.sendi.v1.test;

import lombok.*;

@Builder
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
