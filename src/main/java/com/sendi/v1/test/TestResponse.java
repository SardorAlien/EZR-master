package com.sendi.v1.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class TestResponse {
    private Set<TrueFalseQuestion> trueFalseQuestions;
    private Set<MultipleChoiceQuestion> multipleChoiceQuestions;
    private Set<MatchingQuestion> matchingQuestions;
    private Set<WrittenQuestion> writtenQuestions;
}
