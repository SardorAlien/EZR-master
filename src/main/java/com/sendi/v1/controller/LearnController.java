package com.sendi.v1.controller;

import com.sendi.v1.security.config.permission.DeckReadPermission;
import com.sendi.v1.security.config.permission.DeckUpdatePermission;
import com.sendi.v1.service.model.FlashcardDTO;
import com.sendi.v1.service.LearnService;
import com.sendi.v1.service.model.FlashcardImageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping( value = "/api/v1/learn")
public class LearnController {
    private final LearnService learnService;

    @DeckReadPermission
    @GetMapping("{userId}/{deckId}/session")
    public ResponseEntity<List<FlashcardImageDTO>> beginLearningSession(@PathVariable Long deckId, @PathVariable String userId) {
        return ResponseEntity.ok(learnService.beginLearningSession(deckId));
    }

    @DeckUpdatePermission
    @PostMapping("{userId}/{deckId}/end")
    public ResponseEntity<String> finishLearningSession(@PathVariable Long deckId, @RequestBody List<Long> flashcardIds, @PathVariable String userId) {
        learnService.finishLearningSession(deckId, flashcardIds);

        return ResponseEntity.ok("Learning session ended");
    }

}
