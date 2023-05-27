package com.sendi.v1.test;

import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.util.Randomizing;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

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

    public MultipleChoiceQuestion prepareQuestion(Flashcard flashcard, List<Flashcard> flashcardsWillBeEliminated, AnswerWith answerWith) {
        int[] randomFlashcardIds = Randomizing.getRandomFlashcardIdsForSecondaryAnswers(flashcardsWillBeEliminated.size());

        String actualQuestion = QuestionAnswerUtil.getQuestion(flashcard, answerWith);
        String actualAnswer = QuestionAnswerUtil.getAnswer(flashcard, answerWith);

        Flashcard flashcard1 = flashcardsWillBeEliminated.get(randomFlashcardIds[0]);
        Flashcard flashcard2 = flashcardsWillBeEliminated.get(randomFlashcardIds[1]);
        Flashcard flashcard3 = flashcardsWillBeEliminated.get(randomFlashcardIds[2]);
        String secondaryAnswer1 = QuestionAnswerUtil.getAnswer(flashcard1, answerWith);
        String secondaryAnswer2 = QuestionAnswerUtil.getAnswer(flashcard2, answerWith);
        String secondaryAnswer3 = QuestionAnswerUtil.getAnswer(flashcard3, answerWith);

        MultipleChoiceQuestion multipleChoiceQuestion = MultipleChoiceQuestion.builder()
                .question(actualQuestion)
                .actualAnswer(actualAnswer)
                .secondaryAnswer1(secondaryAnswer1)
                .secondaryAnswer2(secondaryAnswer2)
                .secondaryAnswer3(secondaryAnswer3)
                .flashcardId(flashcard.getId())
                .build();

        return multipleChoiceQuestion;
    }
}
