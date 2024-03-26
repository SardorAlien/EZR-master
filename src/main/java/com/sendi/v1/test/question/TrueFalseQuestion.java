package com.sendi.v1.test.question;

import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.test.AnswerWith;
import com.sendi.v1.test.QuestionAnswerUtil;
import com.sendi.v1.util.Randomizing;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TrueFalseQuestion extends Question {
    private String actualAnswer;
    private String answerForUser;

    public TrueFalseQuestion prepareQuestion(Flashcard flashcard,
                                             List<Flashcard> flashcardsWillBeEliminated,
                                             AnswerWith answerWith) {
        String actualQuestion = QuestionAnswerUtil.getQuestion(flashcard, answerWith);;
        String actualAnswer = QuestionAnswerUtil.getAnswer(flashcard, answerWith);
        String answerForUser = getAnswerForUser(flashcard, flashcardsWillBeEliminated, answerWith);

        return TrueFalseQuestion.builder()
                .question(actualQuestion)
                .actualAnswer(actualAnswer)
                .answerForUser(answerForUser)
                .flashcardId(flashcard.getId())
                .build();
    }

    private String getAnswerForUser(Flashcard flashcard,
                                    List<Flashcard> flashcardsWillBeEliminated,
                                    AnswerWith answerWith) {
        int randomFlashcardId = Randomizing.getRandomNumber(flashcardsWillBeEliminated.size());
        int randomNumberForAnswerForUser = Randomizing.getRandomNumber(2);

        if (randomNumberForAnswerForUser == 1) {
            flashcard = flashcardsWillBeEliminated.get(randomFlashcardId);
        }

        String answerForUser = QuestionAnswerUtil.getAnswer(flashcard, answerWith);

        return answerForUser;
    }
}
