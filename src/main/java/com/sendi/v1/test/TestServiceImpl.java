package com.sendi.v1.test;

import com.sendi.v1.exception.custom.QuestionCountException;
import com.sendi.v1.test.question.TestProducerService;
import com.sendi.v1.test.question.TestQuestions;
import com.sendi.v1.test.question.TestRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestProducerService testQuestionsProducerService;
    private final TestCheckerService testCheckerService;

    @Override
    public TestQuestions getTest(long deckId, TestRequest testRequest) {
        if (testRequest.getQuestionCount() < 20) {
            throw new QuestionCountException(testRequest.getQuestionCount());
        }

        return testQuestionsProducerService.make(deckId, testRequest);
    }

    @Override
    public TestResult submitTest(long deckId, TestResultRequest testResultRequest) {
        return testCheckerService.checkAndGetTestResult(deckId, testResultRequest);
    }
}
