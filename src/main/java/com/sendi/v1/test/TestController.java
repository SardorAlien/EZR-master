package com.sendi.v1.test;

import com.sendi.v1.security.config.permission.DeckReadPermission;
import com.sendi.v1.security.config.permission.DeckUpdatePermission;
import com.sendi.v1.test.question.TestQuestions;
import com.sendi.v1.test.question.TestRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/test")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class TestController {
    private final TestService testService;

    @DeckReadPermission
    @GetMapping("{userId}/{deckId}")
    public ResponseEntity<TestQuestions> beginTest(@PathVariable Long deckId, @RequestBody TestRequest testRequest, @PathVariable String userId) {
        return ResponseEntity.ok(testService.getTest(deckId, testRequest));
    }

    @DeckUpdatePermission
    @PostMapping("{userId}/{deckId}")
    public ResponseEntity<TestResult> submitTest(@PathVariable Long deckId, @RequestBody TestResultRequest requestForTest, @PathVariable String userId) {
        return ResponseEntity.ok(testService.submitTest(deckId, requestForTest));
    }
}
