package com.sendi.v1.controller;

import com.sendi.v1.dto.DeckDTO;
import com.sendi.v1.service.DeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/v1/deck")
@RequiredArgsConstructor
public class DeckController {
    private final DeckService deckService;

    @GetMapping(value = "/{userId}/all")
    public ResponseEntity<List<DeckDTO>> getAllDecks(@PathVariable Long userId) {
        return ResponseEntity.ok(deckService.getDecksByUserId(userId));
    }

    @GetMapping(value = "/{userId}/all", name = "getAllDecksWithPagination", params = {"page", "size"})
    public ResponseEntity<List<DeckDTO>> getAllDecks(@PathParam("page") int page,
                                                     @PathParam("size") int size,
                                                     @PathVariable Long userId) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return ResponseEntity.ok(deckService.getDecksByUserId(userId, pageRequest));
    }

    @PostMapping("/{userId}/new")
    public ResponseEntity<DeckDTO> createDeck(@PathVariable Long userId, @RequestBody DeckDTO deckDTO) {
        return ResponseEntity.ok(deckService.createOrUpdate(userId, deckDTO));
    }

    @PutMapping("/{userId}/edit")
    public ResponseEntity<DeckDTO> updateDeck(@PathVariable Long userId, @RequestBody DeckDTO deckDTO) {
        return ResponseEntity.ok(deckService.createOrUpdate(userId, deckDTO));
    }

    @DeleteMapping("/{deckId}")
    public ResponseEntity<String> deleteDeck(@PathVariable Long deckId) {
        deckService.deleteById(deckId);

        return ResponseEntity.ok("Deleted successfully");
    }

    @GetMapping("/{deckId}")
    public ResponseEntity<DeckDTO> getDeck(@PathVariable Long deckId) {
        return ResponseEntity.ok(deckService.getOneById(deckId));
    }
}
