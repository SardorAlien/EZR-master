package com.sendi.v1.repo;

import com.sendi.v1.domain.Streak;
import com.sendi.v1.service.model.StreakDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;
import java.util.Optional;

public interface StreakRepository extends PagingAndSortingRepository<Streak, Long> {
    List<Streak> findAllByUserId(Long userId);

    Streak findTopByUserIdOrderByLastStreakDateTimeDesc(Long userId);

    Streak findTopByUserIdOrderByCurrentStreakAsc(Long userId);
}
