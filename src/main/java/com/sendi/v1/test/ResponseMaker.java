package com.sendi.v1.test;

import com.sendi.v1.domain.Flashcard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class ResponseMaker {
    private final QuestionMaker questionMaker;

    private TestRequest testRequest;
    private TestResponse testResponse;
    private List<Flashcard> flashcardsWillBeEliminated;
    private int sizeSlicer;
    private int actualSizeFlashcards;

    @Autowired
    public ResponseMaker(QuestionMaker questionMaker) {
        this.questionMaker = questionMaker;
    }

    public TestResponse make(long deckId, TestRequest testRequest) {
        this.testRequest = testRequest;
        testResponse = new TestResponse();
        flashcardsWillBeEliminated = questionMaker.getFlashcardsAndShuffleByQuestionCount(deckId, testRequest.getQuestionCount());
        actualSizeFlashcards = flashcardsWillBeEliminated.size();
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
                addTrueFalseQuestion();
            }
        }
    }

    private void addTrueFalseQuestion() {
        Flashcard flashcard = getRandomFlashcardAndRemoveFromList();

        String answerForUser = getAnswerForUser(flashcard);

        String actualQuestion = "";
        String actualAnswer = "";
        if (testRequest.getAnswerWith() == AnswerWith.DEFINITION) {
            actualQuestion = flashcard.getTerm();
            actualAnswer = flashcard.getDefinition();
        } else if (testRequest.getAnswerWith() == AnswerWith.TERM) {
            actualQuestion = flashcard.getDefinition();
            actualAnswer = flashcard.getTerm();
        } else {

        }

        TrueFalseQuestion trueFalseQuestion = TrueFalseQuestion.builder()
                .question(actualQuestion)
                .actualAnswer(actualAnswer)
                .answerForUser(answerForUser)
                .flashcardId(flashcard.getId())
                .build();

        testResponse.addTrueFalseQuestion(trueFalseQuestion);
    }

    private String getAnswerForUser(Flashcard flashcard) {
        int randomFlashcardId = getRandomNumber(flashcardsWillBeEliminated.size());
        int randomNumberForAnswerForUser = getRandomNumber(2);

        if (randomNumberForAnswerForUser == 1) {
            flashcard = flashcardsWillBeEliminated.get(randomFlashcardId);
        }

        String answerForUser = "";
        if (testRequest.getAnswerWith() == AnswerWith.DEFINITION) {
            answerForUser = flashcard.getDefinition();
        } else if (testRequest.getAnswerWith() == AnswerWith.TERM) {
            answerForUser = flashcard.getTerm();
        } else {

        }

        return answerForUser;
    }

    private void addMultipleChoiceQuestionsIfIncluded() {
        int remainder = actualSizeFlashcards % getCountQuestionTypes();

        if (testRequest.isMultipleChoiceIncluded()) {
            for (int i = 0; i < sizeSlicer + remainder; i++) {
                addMultipleChoiceQuestion();
            }
        }
    }

    private void addMultipleChoiceQuestion() {
        Flashcard flashcard = getRandomFlashcardAndRemoveFromList();
        int[] randomFlashcardIds = getRandomFlashcardIds();

        String actualQuestion = "";
        String actualAnswer = "";
        String secondaryAnswer1 = "";
        String secondaryAnswer2 = "";
        String secondaryAnswer3 = "";

        if (testRequest.getAnswerWith() == AnswerWith.DEFINITION) {
            actualQuestion = flashcard.getTerm();
            actualAnswer = flashcard.getDefinition();
            secondaryAnswer1 = flashcardsWillBeEliminated.get(randomFlashcardIds[0]).getDefinition();
            secondaryAnswer2 = flashcardsWillBeEliminated.get(randomFlashcardIds[1]).getDefinition();
            secondaryAnswer3 = flashcardsWillBeEliminated.get(randomFlashcardIds[2]).getDefinition();

        } else if (testRequest.getAnswerWith() == AnswerWith.TERM) {
            actualQuestion = flashcard.getDefinition();
            actualAnswer = flashcard.getTerm();

            secondaryAnswer1 = flashcardsWillBeEliminated.get(randomFlashcardIds[0]).getTerm();
            secondaryAnswer2 = flashcardsWillBeEliminated.get(randomFlashcardIds[1]).getTerm();
            secondaryAnswer3 = flashcardsWillBeEliminated.get(randomFlashcardIds[2]).getTerm();
        } else {

        }

        MultipleChoiceQuestion multipleChoiceQuestion = MultipleChoiceQuestion.builder()
                .question(actualQuestion)
                .actualAnswer(actualAnswer)
                .secondaryAnswer1(secondaryAnswer1)
                .secondaryAnswer2(secondaryAnswer2)
                .secondaryAnswer3(secondaryAnswer3)
                .flashcardId(flashcard.getId())
                .build();

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
        Flashcard flashcard = getRandomFlashcardAndRemoveFromList();

        String actualQuestion = "";
        String actualAnswer = "";
        if (testRequest.getAnswerWith() == AnswerWith.DEFINITION) {
            actualQuestion = flashcard.getTerm();
            actualAnswer = flashcard.getDefinition();
        } else if (testRequest.getAnswerWith() == AnswerWith.TERM) {
            actualQuestion = flashcard.getDefinition();
            actualAnswer = flashcard.getTerm();
        } else {

        }

        MatchingQuestion matchingQuestion = MatchingQuestion.builder()
                .question(actualQuestion)
                .answer(actualAnswer)
                .flashcardId(flashcard.getId())
                .build();

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
        Flashcard flashcard = getRandomFlashcardAndRemoveFromList();

        String actualQuestion = "";
        String actualAnswer = "";
        if (testRequest.getAnswerWith() == AnswerWith.DEFINITION) {
            actualQuestion = flashcard.getTerm();
            actualAnswer = flashcard.getDefinition();
        } else if (testRequest.getAnswerWith() == AnswerWith.TERM) {
            actualQuestion = flashcard.getDefinition();
            actualAnswer = flashcard.getTerm();
        } else {

        }

        WrittenQuestion writtenQuestion = WrittenQuestion.builder()
                .question(actualQuestion)
                .answer(actualAnswer)
                .flashcardId(flashcard.getId())
                .build();

        testResponse.addWrittenQuestion(writtenQuestion);
    }

    private Flashcard getRandomFlashcardAndRemoveFromList() {
        log.info("getRandomFlashcardAndRemoveFromList truefalse size => {}", testResponse.getTrueFalseQuestions().size());
        log.info("getRandomFlashcardAndRemoveFromList multiple size => {}", testResponse.getMultipleChoiceQuestions().size());
        log.info("getRandomFlashcardAndRemoveFromList matching size => {}", testResponse.getMatchingQuestions().size());
        log.info("getRandomFlashcardAndRemoveFromList written size => {}", testResponse.getWrittenQuestions().size());
        log.info("getRandomFlashcardAndRemoveFromList flashcards size => {}", flashcardsWillBeEliminated.size());

        int randomFlashcardId = getRandomNumber(flashcardsWillBeEliminated.size());

        Flashcard flashcard = flashcardsWillBeEliminated.get(randomFlashcardId);
        flashcardsWillBeEliminated.remove(randomFlashcardId);

        return flashcard;
    }

    private int getRandomNumber(int boundary) {
        return new Random()
                .ints(0, boundary)
                .findFirst()
                .getAsInt();
    }

    private void makeSizeSlicer() {
        sizeSlicer = flashcardsWillBeEliminated.size() / getCountQuestionTypes();
    }

    private int getCountQuestionTypes() {
        int countQuestionTypes = 0;
        if (testRequest.isTrueFalseQuestionsIncluded()) countQuestionTypes++;
        if (testRequest.isMultipleChoiceIncluded()) countQuestionTypes++;
        if (testRequest.isMatchingQuestionsIncluded()) countQuestionTypes++;
        if (testRequest.isWrittenIncluded()) countQuestionTypes++;

        return countQuestionTypes;
    }

    private int[] getRandomFlashcardIds() {
        int[] randomFlashcardIds = new int[3];
        randomFlashcardIds[0] = getRandomNumber(flashcardsWillBeEliminated.size());
        while (randomFlashcardIds[1] == randomFlashcardIds[0]) {
            randomFlashcardIds[1] = getRandomNumber(flashcardsWillBeEliminated.size());
        }

        while (randomFlashcardIds[2] == randomFlashcardIds[0] ||
                randomFlashcardIds[2] == randomFlashcardIds[1]) {
            randomFlashcardIds[2] = getRandomNumber(flashcardsWillBeEliminated.size());
        }

        return randomFlashcardIds;
    }
}
