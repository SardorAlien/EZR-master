package com.sendi.v1.controller;

import com.sendi.v1.dto.FlashcardDTO;
import com.sendi.v1.service.FlashcardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/flashcard")
@RequiredArgsConstructor
public class FlashcardController {
    private final FlashcardService flashcardService;

    @GetMapping("/{deckId}/all")
    public ResponseEntity<List<FlashcardDTO>> getAllFlashcards(@PathVariable Long deckId) {
        return ResponseEntity.ok(flashcardService.getFlashcardsByDeckId(deckId));
    }

    @GetMapping(value = "/{deckId}/all", params = {"page", "size"})
    public ResponseEntity<List<FlashcardDTO>> getAllFlashcards(@RequestParam int page,
                                                               @RequestParam int size,
                                                               @PathVariable Long deckId) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return ResponseEntity.ok(flashcardService.getFlashcardsByDeckId(deckId, pageRequest));
    }

    @PostMapping("/{deckId}/new")
    public ResponseEntity<FlashcardDTO> createFlashcard(@PathVariable Long deckId, @RequestBody FlashcardDTO flashcardDTO) {
        return ResponseEntity.ok(flashcardService.createOrUpdate(deckId, flashcardDTO));
    }

    @PutMapping("/{deckId}/edit")
    public ResponseEntity<FlashcardDTO> updateFlashcard(@PathVariable Long deckId, @RequestBody FlashcardDTO flashcardDTO) {
        return ResponseEntity.ok(flashcardService.createOrUpdate(deckId, flashcardDTO));
    }

    @DeleteMapping("/{flashcardId}")
    public ResponseEntity<String> deleteFlashcard(@PathVariable Long flashcardId) {
        flashcardService.deleteById(flashcardId);

        return ResponseEntity.ok("Deleted successfully");
    }

    @GetMapping("/{flashcardId}")
    public ResponseEntity<FlashcardDTO> getFlashcard(@PathVariable Long flashcardId) {
        return ResponseEntity.ok(flashcardService.getOneById(flashcardId));
    }
}
