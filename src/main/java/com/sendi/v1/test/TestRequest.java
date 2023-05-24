package com.sendi.v1.test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestRequest {
    private int questionCount;

    @JsonProperty("isTrueFalseQuestionsIncluded")
    private boolean isTrueFalseQuestionsIncluded;

    @JsonProperty("isMultipleChoiceIncluded")
    private boolean isMultipleChoiceIncluded;

    @JsonProperty("isMatchingQuestionsIncluded")
    private boolean isMatchingQuestionsIncluded;

    @JsonProperty("isWrittenIncluded")
    private boolean isWrittenIncluded;

    private AnswerWith answerWith;
}
