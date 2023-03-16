package com.sendi.v1.controller;

import com.sendi.v1.dto.DeckDTO;
import com.sendi.v1.dto.FlashcardDTO;
import com.sendi.v1.service.FlashcardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/flashcard")
@RequiredArgsConstructor
public class FlashcardController {
    private final FlashcardService flashcardService;

    @GetMapping("/flashcards")
    public ResponseEntity<List<FlashcardDTO>> getFlashcards(@RequestBody DeckDTO deckDTO) {
        return ResponseEntity.ok(flashcardService.getFlashcardsByDeck(deckDTO));
    }

    @PostMapping("/new/{deckId}")
    public ResponseEntity<FlashcardDTO> createFlashcard(@RequestBody FlashcardDTO flashcardDTO) {
        return ResponseEntity.ok(flashcardService.createOrUpdateDeck(flashcardDTO));
    }

    @PostMapping
    public ResponseEntity<FlashcardDTO> updateFlashcard(@RequestBody FlashcardDTO flashcardDTO) {
        return ResponseEntity.ok(flashcardService.createOrUpdateDeck(flashcardDTO));
    }

    @DeleteMapping("/{flashcardId}")
    public ResponseEntity<String> deleteFlashcard(@PathVariable Long flashcardId) {
        flashcardService.deleteById(flashcardId);

        return ResponseEntity.ok("Deleted successfully");
    }

    @GetMapping("/{flashcardId}")
    public ResponseEntity<FlashcardDTO> getFlashcard(@PathVariable Long flashcardId) {
        return ResponseEntity.ok(flashcardService.getDeckById(flashcardId));
    }
}
