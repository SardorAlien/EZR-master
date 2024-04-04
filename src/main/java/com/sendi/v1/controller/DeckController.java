package com.sendi.v1.controller;

import com.sendi.v1.service.ShareDeckService;
import com.sendi.v1.service.model.DeckDTO;
import com.sendi.v1.security.config.permission.DeckCreatePermission;
import com.sendi.v1.security.config.permission.DeckDeletePermission;
import com.sendi.v1.security.config.permission.DeckReadPermission;
import com.sendi.v1.security.config.permission.DeckUpdatePermission;
import com.sendi.v1.service.DeckService;
import com.sendi.v1.service.model.ShareWith;
import com.sendi.v1.test.*;
import com.sendi.v1.test.question.TestQuestions;
import com.sendi.v1.test.question.TestRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/decks")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class DeckController {
    private final DeckService deckService;
    private final ShareDeckService shareDeckService;

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
        return ResponseEntity.ok(deckService.getDecksByUserId(userId, page, size));
    }

    @DeckReadPermission
    @GetMapping(value = "{userId}/all/brief")
    public ResponseEntity<List<DeckDTO>> getAllDeckInfoWithoutFlashcards(@PathVariable Long userId) {
        return ResponseEntity.ok(deckService.getDecksInfoByUserId(userId));
    }

    @DeckReadPermission
    @GetMapping(value = "{userId}/all/brief", name = "getAllDecksWithoutFlashcards", params = {"page", "size"})
    public ResponseEntity<List<DeckDTO>> getAllDeckInfoWithoutFlashcards(@RequestParam("page") int page,
                                                                         @RequestParam("size") int size,
                                                                         @PathVariable Long userId) {
        return ResponseEntity.ok(deckService.getDecksInfoByUserId(userId, page, size));
    }

    @DeckCreatePermission
    @PostMapping("{userId}")
    public ResponseEntity<DeckDTO> createDeckByUserId(@PathVariable Long userId, @RequestBody DeckDTO deckDTO) {
        System.out.println("visibility: " + deckDTO.getDeckVisibility());
        return new ResponseEntity<>(deckService.createOrUpdate(userId, deckDTO), HttpStatus.CREATED);
    }

    @DeckUpdatePermission
    @PutMapping("{userId}")
    public ResponseEntity<DeckDTO> updateDeckByUserId(@PathVariable Long userId, @RequestBody DeckDTO deckDTO) {
        return ResponseEntity.ok(deckService.createOrUpdate(userId, deckDTO));
    }

    @DeckDeletePermission
    @DeleteMapping("{userId}/{deckId}")
    public ResponseEntity<String> deleteDeck(@PathVariable Long userId, @PathVariable Long deckId) {
        deckService.deleteById(deckId);

        return ResponseEntity.ok("Deleted successfully");
    }

    @DeckReadPermission
    @GetMapping("{userId}/{deckId}")
    public ResponseEntity<DeckDTO> getDeck(@PathVariable Long userId, @PathVariable Long deckId) {
        return ResponseEntity.ok(deckService.getOneById(deckId));
    }

    @DeckReadPermission
    @GetMapping("{userId}/only/{deckId}")
    public ResponseEntity<DeckDTO> getDeckWithoutFlashcards(@PathVariable Long userId, @PathVariable Long deckId) {
        return ResponseEntity.ok(deckService.getOneByIdWithoutFlashcards(deckId));
    }

    @DeckReadPermission
    @GetMapping("{userId}/share/{deckId}")
    public ResponseEntity<?> shareDeck(@PathVariable Long userId, @RequestBody ShareWith shareWith, @PathVariable Long deckId) {
        return ResponseEntity.ok(shareDeckService.shareDeckWithEmail(userId, shareWith, deckId));
    }
}
