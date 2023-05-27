package com.sendi.v1.test;

import com.sendi.v1.domain.Flashcard;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MatchingQuestion extends Question {
    private String answer;

    public MatchingQuestion prepareQuestion(Flashcard flashcard, AnswerWith answerWith) {
        String actualQuestion = QuestionAnswerUtil.getQuestion(flashcard, answerWith);
        String actualAnswer = QuestionAnswerUtil.getQuestion(flashcard, answerWith);

        MatchingQuestion matchingQuestion = MatchingQuestion.builder()
                .question(actualQuestion)
                .answer(actualAnswer)
                .flashcardId(flashcard.getId())
                .build();

        return matchingQuestion;
    }
}
