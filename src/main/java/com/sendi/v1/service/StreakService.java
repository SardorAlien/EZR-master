package com.sendi.v1.service;

import com.sendi.v1.domain.Streak;
import com.sendi.v1.repo.StreakRepository;
import com.sendi.v1.service.model.StreakDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StreakService {
    private final StreakRepository streakRepository;

    public StreakDTO getCurrentStreak(Long userId) {
        Streak streak = streakRepository.findTopByUserIdOrderByLastStreakDateTimeDesc(userId);
        return new StreakDTO(streak.getId(), streak.getUser().getId(), streak.getCurrentStreak());
    }

    public StreakDTO getLongestStreak(Long userId) {
        Streak streak = streakRepository.findTopByUserIdOrderByCurrentStreakAsc(userId);
        return new StreakDTO(streak.getId(), streak.getUser().getId(), streak.getCurrentStreak());
    }
}
