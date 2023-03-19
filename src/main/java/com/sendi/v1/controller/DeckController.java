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

    @GetMapping("/{userId}/all")
    public ResponseEntity<List<DeckDTO>> getAllDecks(@PathVariable Long userId) {
        return ResponseEntity.ok(deckService.getDecksByUserId(userId));
    }

    @PostMapping("{userId}/new")
    public ResponseEntity<DeckDTO> createDeck(@PathVariable Long userId, @RequestBody DeckDTO deckDTO) {
        return ResponseEntity.ok(deckService.createOrUpdateDeck(userId, deckDTO));
    }

    @PostMapping("{userId}/change")
    public ResponseEntity<DeckDTO> updateDeck(@PathVariable Long userId, @RequestBody DeckDTO deckDTO) {
        return ResponseEntity.ok(deckService.createOrUpdateDeck(userId, deckDTO));
    }

    @DeleteMapping("/{deckId}")
    public ResponseEntity<String> deleteDeck(@PathVariable Long deckId) {
        deckService.deleteById(deckId);

        return ResponseEntity.ok("Deleted successfully");
    }

    @GetMapping("/{deckId}")
    public ResponseEntity<DeckDTO> getDeck(@PathVariable Long deckId) {
        return ResponseEntity.ok(deckService.getDeckById(deckId));
    }
}
