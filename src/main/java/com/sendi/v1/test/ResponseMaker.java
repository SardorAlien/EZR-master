package com.sendi.v1.test;

import com.sendi.v1.domain.Flashcard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ResponseMaker {
    private final FlashcardsForTestService flashcardsForTestService;

    private TestRequest testRequest;
    private TestResponse testResponse;
    private int sizeSlicer;

    @Autowired
    public ResponseMaker(FlashcardsForTestService flashcardsForTestService) {
        this.flashcardsForTestService = flashcardsForTestService;
    }

    public TestResponse make(long deckId, TestRequest testRequest) {
        this.testRequest = testRequest;
        testResponse = new TestResponse();

        flashcardsForTestService.getFlashcardsByQuestionCountAndShuffle(deckId, testRequest.getQuestionCount());
        generateResponse();

        return testResponse;
    }

    public TestResponse generateResponse() {
        makeSizeSlicer();
        addTrueFalseQuestionsIfIncluded();
        addMultipleChoiceQuestionsIfIncluded();
        addMatchingQuestionsIfIncluded();
        addWrittenQuestionsIfIncluded();
        return testResponse;
    }

    private void addTrueFalseQuestionsIfIncluded() {
        if (testRequest.isTrueFalseQuestionsIncluded()) {
            for (int i = 0; i < sizeSlicer; i++) {
                addTrueFalseQuestionToTestResponse();
            }
        }
    }

    private void addTrueFalseQuestionToTestResponse() {
        Flashcard flashcard = flashcardsForTestService.getRandomFlashcardAndRemoveFromList();

        TrueFalseQuestion trueFalseQuestion = new TrueFalseQuestion()
                .prepareQuestion(flashcard, flashcardsForTestService.getFlashcards(), testRequest.getAnswerWith());

        testResponse.addTrueFalseQuestion(trueFalseQuestion);
    }


    private void addMultipleChoiceQuestionsIfIncluded() {
        int remainder = flashcardsForTestService.getActualSizeFlashcards() % getCountQuestionTypes();

        if (testRequest.isMultipleChoiceIncluded()) {
            for (int i = 0; i < sizeSlicer + remainder; i++) {
                addMultipleChoiceQuestion();
            }
        }
    }

    private void addMultipleChoiceQuestion() {
        Flashcard flashcard = flashcardsForTestService.getRandomFlashcardAndRemoveFromList();

        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion()
                .prepareQuestion(flashcard, flashcardsForTestService.getFlashcards(), testRequest.getAnswerWith());

        testResponse.addMultipleChoiceQuestion(multipleChoiceQuestion);
    }

    private void addMatchingQuestionsIfIncluded() {
        if (testRequest.isMatchingQuestionsIncluded()) {
            for (int i = 0; i < sizeSlicer; i++) {
                addMatchingQuestion();
            }
        }
    }

    private void addMatchingQuestion() {
        Flashcard flashcard = flashcardsForTestService.getRandomFlashcardAndRemoveFromList();

        MatchingQuestion matchingQuestion = new MatchingQuestion()
                .prepareQuestion(flashcard, testRequest.getAnswerWith());

        testResponse.addMatchingQuestion(matchingQuestion);
    }

    private void addWrittenQuestionsIfIncluded() {
        if (testRequest.isWrittenIncluded()) {
            for (int i = 0; i < sizeSlicer; i++) {
                addWrittenQuestion();
            }
        }
    }

    private void addWrittenQuestion() {
        Flashcard flashcard = flashcardsForTestService.getRandomFlashcardAndRemoveFromList();

        WrittenQuestion writtenQuestion = new WrittenQuestion()
                .prepareQuestion(flashcard, testRequest.getAnswerWith());

        testResponse.addWrittenQuestion(writtenQuestion);
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
