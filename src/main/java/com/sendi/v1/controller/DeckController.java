package com.sendi.v1.controller;

import com.sendi.v1.dto.DeckDTO;
import com.sendi.v1.dto.FlashcardDTO;
import com.sendi.v1.service.DeckService;
import com.sendi.v1.service.FlashcardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deck")
@RequiredArgsConstructor
public class DeckController {
    private final DeckService deckService;
    private final FlashcardService flashcardService;

    @GetMapping("/flashcards")
    public ResponseEntity<List<FlashcardDTO>> getFlashcards(@RequestBody Long deckId) {
        return ResponseEntity.ok(flashcardService.getFlashcardsByDeckId(deckId));
    }

    @GetMapping("/{userId}/all")
    public ResponseEntity<List<DeckDTO>> getAllFlashcards(@PathVariable Long userId) {
        return ResponseEntity.ok(deckService.getDecksByUserId(userId));
    }

    @PostMapping("/new")
    public ResponseEntity<DeckDTO> createDeck(@RequestBody DeckDTO deckDTO) {
        return ResponseEntity.ok(deckService.createOrUpdateDeck(deckDTO));
    }

    @PostMapping
    public ResponseEntity<DeckDTO> updateDeck(@RequestBody DeckDTO deckDTO) {
        return ResponseEntity.ok(deckService.createOrUpdateDeck(deckDTO));
    }

    @DeleteMapping("/{deckId}/delete")
    public ResponseEntity<String> deleteDeck(@PathVariable Long deckId) {
        deckService.deleteById(deckId);

        return ResponseEntity.ok("Deleted successfully");
    }

    @GetMapping("/{deckId}")
    public ResponseEntity<DeckDTO> getDeck(@PathVariable Long deckId) {
        return ResponseEntity.ok(deckService.getDeckById(deckId));
    }
}
