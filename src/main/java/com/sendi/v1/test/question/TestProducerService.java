package com.sendi.v1.test.question;

import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.test.FlashcardsForTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class TestProducerService {
    private final FlashcardsForTestService flashcardsForTestService;

    private TestRequest testRequest;
    private TestQuestions testQuestions;
    private int sizeSlicer;
    private int totalRemainder;
    private int remainders[];

    @Autowired
    public TestProducerService(FlashcardsForTestService flashcardsForTestService) {
        this.flashcardsForTestService = flashcardsForTestService;
    }

    public TestQuestions make(long deckId, TestRequest testRequest) {
        this.testRequest = testRequest;
        testQuestions = new TestQuestions();

        flashcardsForTestService.getFlashcardsByQuestionCountAndShuffle(deckId, testRequest.getQuestionCount());
        calculateRemainders();
        generateResponse();

        return testQuestions;
    }

    public TestQuestions generateResponse() {
        makeSizeSlicer();
        addTrueFalseQuestionsIfIncluded();
        addMultipleChoiceQuestionsIfIncluded();
        addMatchingQuestionsIfIncluded();
        addWrittenQuestionsIfIncluded();
        return testQuestions;
    }

    private void calculateRemainders() {
        remainders = new int[4];
        totalRemainder = flashcardsForTestService.getActualSizeFlashcards() % getCountQuestionTypes();

        int count = 0;
        while (totalRemainder > 0) {
            remainders[count++] = 1;
            totalRemainder--;
            log.info("remainders {}", remainders);
            log.info("total remainder {}", totalRemainder);
        }
    }

    private void addTrueFalseQuestionsIfIncluded() {
        if (testRequest.isTrueFalseQuestionsIncluded()) {
            for (int i = 0; i < sizeSlicer  + remainders[0]; i++) {
                addTrueFalseQuestionToTestResponse();
            }
        }
    }

    private void addTrueFalseQuestionToTestResponse() {
        Flashcard flashcard = flashcardsForTestService.getRandomFlashcardAndRemoveFromList();

        TrueFalseQuestion trueFalseQuestion = new TrueFalseQuestion()
                .prepareQuestion(flashcard, flashcardsForTestService.getFlashcards(), testRequest.getAnswerWith());

        testQuestions.addTrueFalseQuestion(trueFalseQuestion);
    }


    private void addMultipleChoiceQuestionsIfIncluded() {
        if (testRequest.isMultipleChoiceIncluded()) {
            for (int i = 0; i < sizeSlicer + remainders[1]; i++) {
                addMultipleChoiceQuestion();
            }
        }
    }

    private void addMultipleChoiceQuestion() {
        Flashcard flashcard = flashcardsForTestService.getRandomFlashcardAndRemoveFromList();

        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion()
                .prepareQuestion(flashcard, flashcardsForTestService.getFlashcards(), testRequest.getAnswerWith());

        testQuestions.addMultipleChoiceQuestion(multipleChoiceQuestion);
    }

    private void addMatchingQuestionsIfIncluded() {
        if (testRequest.isMatchingQuestionsIncluded()) {
            for (int i = 0; i < sizeSlicer + remainders[2]; i++) {
                addMatchingQuestion();
            }
        }
    }

    private void addMatchingQuestion() {
        Flashcard flashcard = flashcardsForTestService.getRandomFlashcardAndRemoveFromList();

        MatchingQuestion matchingQuestion = new MatchingQuestion()
                .prepareQuestion(flashcard, testRequest.getAnswerWith());

        testQuestions.addMatchingQuestion(matchingQuestion);
    }

    private void addWrittenQuestionsIfIncluded() {
        if (testRequest.isWrittenIncluded()) {
            for (int i = 0; i < sizeSlicer + remainders[3]; i++) {
                addWrittenQuestion();
            }
        }
    }

    private void addWrittenQuestion() {
        Flashcard flashcard = flashcardsForTestService.getRandomFlashcardAndRemoveFromList();

        WrittenQuestion writtenQuestion = new WrittenQuestion()
                .prepareQuestion(flashcard, testRequest.getAnswerWith());

        testQuestions.addWrittenQuestion(writtenQuestion);
    }


    private void makeSizeSlicer() {
        sizeSlicer = flashcardsForTestService.getActualSizeFlashcards() / getCountQuestionTypes();
    }

    private int getCountQuestionTypes() {
        int countQuestionTypes = 0;
        if (testRequest.isTrueFalseQuestionsIncluded()) countQuestionTypes++;
        if (testRequest.isMultipleChoiceIncluded()) countQuestionTypes++;
        if (testRequest.isMatchingQuestionsIncluded()) countQuestionTypes++;
        if (testRequest.isWrittenIncluded()) countQuestionTypes++;

        return countQuestionTypes;
    }
}
