package com.sendi.v1.test.question;

import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.test.AnswerWith;
import com.sendi.v1.test.QuestionAnswerUtil;
import com.sendi.v1.util.Randomizing;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MultipleChoiceQuestion extends Question {
    private List<String> answers;

    public MultipleChoiceQuestion prepareQuestion(Flashcard flashcard, List<Flashcard> flashcardsWillBeEliminated, AnswerWith answerWith) {
        setQuestion(flashcard, answerWith);
        setAnswers(flashcard, flashcardsWillBeEliminated, answerWith);
        setFlashcardId(flashcard.getId());

        return this;
    }

    private void setQuestion(Flashcard flashcard, AnswerWith answerWith) {
        this.question = QuestionAnswerUtil.getQuestion(flashcard, answerWith);
    }

    private void setAnswers(Flashcard flashcard, List<Flashcard> flashcardsWillBeEliminated, AnswerWith answerWith) {
        List<Flashcard> randomFlashcards = getRandomFlashcards(flashcardsWillBeEliminated);

        List<String> answers = new ArrayList<>();

        String actualAnswer = QuestionAnswerUtil.getAnswer(flashcard, answerWith);
        String answer2 = QuestionAnswerUtil.getAnswer(randomFlashcards.get(0), answerWith);
        String answer3 = QuestionAnswerUtil.getAnswer(randomFlashcards.get(1), answerWith);
        String answer4 = QuestionAnswerUtil.getAnswer(randomFlashcards.get(2), answerWith);

        answers.add(actualAnswer);
        answers.add(answer2);
        answers.add(answer3);
        answers.add(answer4);

        Collections.shuffle(answers);

        this.answers = answers;
    }

    private List<Flashcard> getRandomFlashcards(List<Flashcard> flashcardsWillBeEliminated) {
        int[] randomFlashcardIds = Randomizing.getRandomFlashcardIdsForSecondaryAnswers(flashcardsWillBeEliminated.size());

        Flashcard flashcard1 = flashcardsWillBeEliminated.get(randomFlashcardIds[0]);
        Flashcard flashcard2 = flashcardsWillBeEliminated.get(randomFlashcardIds[1]);
        Flashcard flashcard3 = flashcardsWillBeEliminated.get(randomFlashcardIds[2]);

        return List.of(flashcard1, flashcard2, flashcard3);
    }

}
