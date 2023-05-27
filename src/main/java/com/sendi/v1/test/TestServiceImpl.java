package com.sendi.v1.test;

import com.sendi.v1.exception.custom.QuestionCountException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final ResponseMaker responseMaker;

    @Override
    public TestResponse getTest(long deckId, TestRequest testRequest) {
        if (testRequest.getQuestionCount() < 20) {
            throw new QuestionCountException(testRequest.getQuestionCount());
        }

        return responseMaker.make(deckId, testRequest);
    }
}
