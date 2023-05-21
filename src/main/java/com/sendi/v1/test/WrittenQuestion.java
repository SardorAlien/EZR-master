package com.sendi.v1.test;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class WrittenQuestion extends Question {
    private String answer;
}
