package com.sendi.v1.controller;

import com.sendi.v1.service.model.DeckDTO;
import com.sendi.v1.security.config.permission.DeckCreatePermission;
import com.sendi.v1.security.config.permission.DeckDeletePermission;
import com.sendi.v1.security.config.permission.DeckReadPermission;
import com.sendi.v1.security.config.permission.DeckUpdatePermission;
import com.sendi.v1.service.DeckService;
import com.sendi.v1.test.*;
import com.sendi.v1.test.question.TestQuestions;
import com.sendi.v1.test.question.TestRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/decks")
@RequiredArgsConstructor
public class DeckController {
    private final DeckService deckService;

    @DeckReadPermission
    @GetMapping(value = "{userId}/all")
    public ResponseEntity<List<DeckDTO>> getAllDecksByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(deckService.getDecksByUserId(userId));
    }

    @DeckReadPermission
    @GetMapping(value = "{userId}/all", name = "getAllDecksWithPagination", params = {"page", "size"})
    public ResponseEntity<List<DeckDTO>> getAllDecksByUserId(@RequestParam("page") int page,
                                                     @RequestParam("size") int size,
                                                     @PathVariable Long userId) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return ResponseEntity.ok(deckService.getDecksByUserId(userId, pageRequest));
    }

    @DeckCreatePermission
    @PostMapping("{userId}")
    public ResponseEntity<DeckDTO> createDeckByUserId(@PathVariable Long userId, @RequestBody DeckDTO deckDTO) {
        return new ResponseEntity<>(deckService.createOrUpdate(userId, deckDTO), HttpStatus.CREATED);
    }

    @DeckUpdatePermission
    @PutMapping("{userId}")
    public ResponseEntity<DeckDTO> updateDeckByUserId(@PathVariable Long userId, @RequestBody DeckDTO deckDTO) {
        return ResponseEntity.ok(deckService.createOrUpdate(userId, deckDTO));
    }

    @DeckDeletePermission
    @DeleteMapping("{deckId}")
    public ResponseEntity<String> deleteDeck(@PathVariable Long deckId) {
        deckService.deleteById(deckId);

        return ResponseEntity.ok("Deleted successfully");
    }

    @DeckReadPermission
    @GetMapping("{deckId}")
    public ResponseEntity<DeckDTO> getDeck(@PathVariable Long deckId) {
        return ResponseEntity.ok(deckService.getOneById(deckId));
    }
}
