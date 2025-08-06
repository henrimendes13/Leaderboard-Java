package com.leaderboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardDTO {
    private Long id;
    private String playerName;
    private Integer score;
} 