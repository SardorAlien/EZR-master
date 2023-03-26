package com.sendi.v1.controller;

import com.sendi.v1.dto.FlashcardDTO;
import com.sendi.v1.service.LearnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/learn")
public class LearnController {
    private final LearnService learnService;

    @PostMapping(path = "/{deckId}/learning-session", produces = "application/json")
    public ResponseEntity<List<FlashcardDTO>> beginLearningSession(@PathVariable Long deckId) {
        return ResponseEntity.ok(learnService.beginLearningSession(deckId));
    }

    @PostMapping(path = "/{deckId}/end-learning-session", produces = "application/json")
    public ResponseEntity<String> finishLearningSession(@PathVariable Long deckId, @RequestBody List<Long> learnedFlashcardsId) {
        learnService.finishLearningSession(deckId, learnedFlashcardsId);

        return ResponseEntity.ok("Learning session ended");
    }

}
