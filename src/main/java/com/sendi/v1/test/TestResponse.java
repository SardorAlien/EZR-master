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

    public void addTrueFalseQuestion(TrueFalseQuestion trueFalseQuestion) {
        trueFalseQuestions.add(trueFalseQuestion);
    }

    public void addMultipleChoiceQuestion(MultipleChoiceQuestion multipleChoiceQuestion) {
        multipleChoiceQuestions.add(multipleChoiceQuestion);
    }

    public void addMatchingQuestion(MatchingQuestion matchingQuestion) {
        matchingQuestions.add(matchingQuestion);
    }

    public void addWrittenQuestion(WrittenQuestion writtenQuestion) {
        writtenQuestions.add(writtenQuestion);
    }
}
