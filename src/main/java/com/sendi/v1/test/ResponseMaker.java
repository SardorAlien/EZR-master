package com.sendi.v1.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResponseMaker {

    private QuestionMaker questionMaker;
    private TestResponse testResponse;

    public TestResponse make(long deckId,
                             int questionCount,
                             boolean isTrueFalseQuestionsIncluded,
                             boolean isMultipleChoiceIncluded,
                             boolean isMatchingQuestionsIncluded,
                             boolean isWrittenIncluded,
                             AnswerWith answerWith) {
        return null;
    }
}
