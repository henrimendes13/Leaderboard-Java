package com.leaderboard.repository;

import com.leaderboard.Entities.LeaderboardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaderboardRepository extends JpaRepository<LeaderboardEntity, Long> {
} 