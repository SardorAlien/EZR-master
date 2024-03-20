package com.sendi.v1.test;

import com.sendi.v1.domain.Flashcard;

public class QuestionAnswerUtil {
    public static String getAnswer(Flashcard flashcard, AnswerWith answerWith) {
        if (answerWith == AnswerWith.DEFINITION) {
            return flashcard.getDefinition();
        } else {
            return flashcard.getTerm();
        }
    }

    public static String getQuestion(Flashcard flashcard, AnswerWith answerWith) {
        if (answerWith == AnswerWith.DEFINITION) {
            return flashcard.getTerm();
        } else {
            return flashcard.getDefinition();
        }
    }
}
