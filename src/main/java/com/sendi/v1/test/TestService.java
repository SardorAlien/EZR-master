package com.sendi.v1.test;

public interface TestService {
    TestResponse getTest(long deckId, TestRequest testRequest);
}
