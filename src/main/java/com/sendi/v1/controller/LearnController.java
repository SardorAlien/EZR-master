package com.sendi.v1.controller;

import com.sendi.v1.service.dto.FlashcardDTO;
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

    @PostMapping(value = "{deckId}/session")
    public ResponseEntity<List<FlashcardDTO>> beginLearningSession(@PathVariable Long deckId) {
        return ResponseEntity.ok(learnService.beginLearningSession(deckId));
    }

    @PostMapping(path = "{deckId}/end")
    public ResponseEntity<String> finishLearningSession(@PathVariable Long deckId, @RequestBody List<Long> flashcardIds) {
        learnService.finishLearningSession(deckId, flashcardIds);

        return ResponseEntity.ok("Learning session ended");
    }

}
