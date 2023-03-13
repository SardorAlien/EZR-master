package com.sendi.v1.controller;

import com.sendi.v1.dto.DeckDTO;
import com.sendi.v1.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/learn")
public class LearnController {

    @GetMapping()
    public ResponseEntity<?> beginLearning(@RequestBody DeckDTO deckDTO) {
        return ResponseEntity.ok("af");
    }
}
