package com.sendi.v1.controller;

import com.sendi.v1.dto.FlashcardDTO;
import com.sendi.v1.service.LearnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/learn")
public class LearnController {
    private final LearnService learnService;
    
    @GetMapping(path = "/new-learning-session", produces = "application/json")
    public ResponseEntity<List<FlashcardDTO>> beginLearningSession(@RequestBody Long deckId) {
        return ResponseEntity.ok(learnService.beginLearningSession(deckId));
    }

    @GetMapping(path = "/end-learning-session", produces = "application/json")
    public ResponseEntity<String> finishLearningSession(@RequestBody List<FlashcardDTO> flashcardDTOs) {
        learnService.finishLearningSession(flashcardDTOs);
        return ResponseEntity.ok("Learning session ended");
    }
}
