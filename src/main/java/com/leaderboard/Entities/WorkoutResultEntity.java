package com.leaderboard.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "workout_results")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutResultEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_id", nullable = false)
    private EquipeEntity equipe;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id", nullable = false)
    private WorkoutEntity workout;
    
    @Column(name = "result", nullable = false)
    private String result;
    
    @Column(name = "position")
    private Integer position;
    
    @Column(name = "points")
    private Integer points;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leaderboard_id")
    private LeaderboardEntity leaderboard;
    
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    // Construtor customizado
    public WorkoutResultEntity(EquipeEntity equipe, WorkoutEntity workout, String result) {
        this.equipe = equipe;
        this.workout = workout;
        this.result = result;
    }
    
    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
} 