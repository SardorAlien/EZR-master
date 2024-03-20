package com.sendi.v1.test;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Builder.Default
    private List<RightOrWrongAnswer> rightOrWrongAnswers = new ArrayList<>();
}
