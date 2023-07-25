package com.sendi.v1.test;

import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.exception.custom.MissMatchDeckAndFlashcardException;
import com.sendi.v1.exception.custom.NoSuchFlashcardException;
import com.sendi.v1.exception.custom.NoSuchFlashcardQuestionWithId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestCheckerService {
    private final FlashcardsForTestService flashcardsForTestService;

    public TestResult checkAndGetTestResult(long deckId, TestResultRequest testResultRequest) {
        TestResult testResult = TestResult.builder()
                .totalQuestions(testResultRequest.getTotalQuestions())
                .build();

        int rightAnswers = 0;
        int wrongAnswers = 0;

        List<UserAnswer> answers = testResultRequest.getAnswers();
        for (UserAnswer userAnswer : answers) {
            Flashcard flashcard = Optional.ofNullable(flashcardsForTestService.getByFlashcardId(userAnswer.getFlashcardId()))
                    .orElseThrow(() -> new NoSuchFlashcardException(userAnswer.getFlashcardId()))
                    .get();

            if (flashcard.getDeck().getId() != deckId) {
                throw new MissMatchDeckAndFlashcardException(deckId);
            }

            String actualQuestion = QuestionAnswerUtil.getQuestion(flashcard, testResultRequest.getAnswerWith());
            String actualAnswer = QuestionAnswerUtil.getAnswer(flashcard, testResultRequest.getAnswerWith());

            RightOrWrongAnswer rightOrWrongAnswer = new RightOrWrongAnswer();
            rightOrWrongAnswer.setUserAnswer(userAnswer.getAnswer());
            rightOrWrongAnswer.setQuestion(userAnswer.getQuestion());
            rightOrWrongAnswer.setFlashcardId(userAnswer.getFlashcardId());

            if (actualQuestion.equals(userAnswer.getQuestion())) {
                if (actualAnswer.equals(userAnswer.getAnswer())) {
                    rightAnswers++;
                    rightOrWrongAnswer.setRight(true);
                } else {
                    wrongAnswers++;
                    rightOrWrongAnswer.setRight(false);
                }
            } else {
                throw new NoSuchFlashcardQuestionWithId(userAnswer.getQuestion() + " with " + userAnswer.getFlashcardId());
            }

            testResult.getRightOrWrongAnswers().add(rightOrWrongAnswer);
        }

        int percentage = (rightAnswers * 100) / testResultRequest.getTotalQuestions();

        testResult.setRightAnswersCount(rightAnswers);
        testResult.setWrongAnswersCount(wrongAnswers);
        testResult.setPercentage(percentage);

        return testResult;
    }
}
