package com.sendi.v1.test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestResultRequest {
    private List<UserAnswer> answers;
    private AnswerWith answerWith;
    private int totalQuestions;
}
