package com.sendi.v1.controller;

import com.sendi.v1.security.config.permission.DeckReadPermission;
import com.sendi.v1.service.StreakService;
import com.sendi.v1.service.model.StreakDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/streaks/")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class StreakController {
    private final StreakService streakService;

    @DeckReadPermission
    @GetMapping(value = "{userId}/current")
    public ResponseEntity<StreakDTO> getCurrentStreak(@PathVariable Long userId) {
        return ResponseEntity.ok(streakService.getCurrentStreak(userId));
    }

    @DeckReadPermission
    @GetMapping(value = "{userId}/longest")
    public ResponseEntity<StreakDTO> getLongestStreak(@PathVariable Long userId) {
        return ResponseEntity.ok(streakService.getLongestStreak(userId));
    }
}
