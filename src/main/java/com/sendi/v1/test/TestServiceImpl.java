package com.sendi.v1.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private TestResponse testResponse;
    private ResponseMaker responseMaker;
    private QuestionMaker questionMaker;

    @Override
    public TestResponse getTest(final long deckId,
                                final int questionCount,
                                final boolean isTrueFalseQuestionsIncluded,
                                final boolean isMultipleChoiceIncluded,
                                final boolean isMatchingQuestionsIncluded,
                                final boolean isWrittenIncluded,
                                final AnswerWith answerWith
    ) {
        return responseMaker.make(deckId,
                questionCount,
                isTrueFalseQuestionsIncluded,
                isMultipleChoiceIncluded,
                isMatchingQuestionsIncluded,
                isWrittenIncluded,
                answerWith);

    }

}
