package com.sendi.v1.controller;

import com.sendi.v1.dto.DeckDTO;
import com.sendi.v1.dto.FlashcardDTO;
import com.sendi.v1.service.DeckService;
import com.sendi.v1.service.FlashcardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deck")
@RequiredArgsConstructor
public class DeckController {
    private final DeckService deckService;
    private final FlashcardService flashcardService;

    @GetMapping("/flashcards")
    public ResponseEntity<List<FlashcardDTO>> getFlashcards(@RequestBody DeckDTO deckDTO) {
        return ResponseEntity.ok(flashcardService.getFlashcardsByDeck(deckDTO));
    }
}
