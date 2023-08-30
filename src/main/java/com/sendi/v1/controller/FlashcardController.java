package com.sendi.v1.controller;

import com.sendi.v1.service.model.FlashcardDTO;
import com.sendi.v1.security.config.permission.FlashcardCreatePermission;
import com.sendi.v1.security.config.permission.FlashcardDeletePermission;
import com.sendi.v1.security.config.permission.FlashcardReadPermission;
import com.sendi.v1.security.config.permission.FlashcardUpdatePermission;
import com.sendi.v1.service.FlashcardService;
import com.sendi.v1.service.model.FlashcardDTORepresentable;
import com.sendi.v1.service.model.FlashcardImageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/flashcards")
@RequiredArgsConstructor
@Slf4j
public class FlashcardController {
    private final FlashcardService flashcardService;

    @FlashcardReadPermission
    @GetMapping("{deckId}/all")
    public ResponseEntity<List<FlashcardImageDTO>> getAllFlashcards(@PathVariable Long deckId) {
        return ResponseEntity.ok(flashcardService.getFlashcardsByDeckId(deckId));
    }

    @FlashcardReadPermission
    @GetMapping(value = "{deckId}/all", params = {"page", "size"})
    public ResponseEntity<List<FlashcardImageDTO>> getAllFlashcards(@RequestParam int page,
                                                                    @RequestParam int size,
                                                                    @PathVariable Long deckId) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return ResponseEntity.ok(flashcardService.getFlashcardsByDeckId(deckId, pageRequest));
    }

    @FlashcardCreatePermission
    @PostMapping(value = "{deckId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createFlashcard(@PathVariable Long deckId,
                                             @RequestPart List<FlashcardDTO> flashcardDTOList,
                                             HttpServletRequest httpServletRequest
    ) throws IOException {
        return ResponseEntity.ok()
                .body(flashcardService.createOrUpdate(deckId, flashcardDTOList, httpServletRequest));
    }

    @FlashcardCreatePermission
    @PostMapping(value = "{deckId}/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<FlashcardDTO>> createFlashcard(@PathVariable Long deckId,
                                                              @RequestParam("excFile") MultipartFile multipartFile
    ) throws IOException {
        return ResponseEntity.ok()
                .body(flashcardService.createOrUpdate(deckId, multipartFile));
    }

    @FlashcardUpdatePermission
    @PutMapping(value = "{deckId}")
    public ResponseEntity<List<FlashcardDTO>> updateFlashcard(@PathVariable Long deckId,
                                                              @RequestBody List<FlashcardDTO> flashcardDTOs
    ) throws IOException {
        return ResponseEntity.ok().body(flashcardService.createOrUpdate(deckId, flashcardDTOs));
    }

    @FlashcardDeletePermission
    @DeleteMapping("{deckId}")
    public ResponseEntity<String> deleteFlashcard(@PathVariable Long deckId, @RequestBody Long flashcardId) {
        flashcardService.deleteById(flashcardId);

        return ResponseEntity.ok("Deleted successfully");
    }

    @FlashcardReadPermission
    @GetMapping("{deckId}/{flashcardId}")
    public ResponseEntity<FlashcardDTORepresentable> getFlashcard(@PathVariable Long deckId, @PathVariable Long flashcardId) {
        return ResponseEntity.ok(flashcardService.getOneById(flashcardId));
    }
}
