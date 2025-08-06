package com.leaderboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutResultDTO {
    
    private Long id;
    private Long equipeId;
    private String equipeNome;
    private Long workoutId;
    private String workoutNome;
    private String result;
    private Integer position;
    private Integer points;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    
    // Construtor customizado
    public WorkoutResultDTO(Long equipeId, Long workoutId, String result) {
        this.equipeId = equipeId;
        this.workoutId = workoutId;
        this.result = result;
    }
} 