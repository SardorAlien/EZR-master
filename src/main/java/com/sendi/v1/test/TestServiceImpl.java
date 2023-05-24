package com.sendi.v1.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

//    private TestResponse testResponse;
    private final ResponseMaker responseMaker;
//    private QuestionMaker questionMaker;

    @Override
    public TestResponse getTest(long deckId, TestRequest testRequest) {
        return responseMaker.make(deckId, testRequest);
    }
}
