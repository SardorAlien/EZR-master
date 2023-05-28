package com.sendi.v1.test;

import com.sendi.v1.test.question.TestQuestions;
import com.sendi.v1.test.question.TestRequest;

public interface TestService {
    TestQuestions getTest(long deckId, TestRequest testRequest);
    TestResult submitTest(long deckId, TestResultRequest testResultRequest);
}
