package com.sendi.v1.test;

public interface TestService {
    TestResponse getTest(long deckId,
                         int questionCount,
                         boolean isTrueFalseIncluded,
                         boolean isMultipleChoiceIncluded,
                         boolean isMatchingIncluded,
                         boolean isWrittenIncluded,
                         AnswerWith answerWith);
}
