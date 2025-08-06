package com.leaderboard.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "leaderboard")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(mappedBy = "leaderboard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EquipeEntity> equipes;
    
    @OneToMany(mappedBy = "leaderboard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkoutEntity> workouts;
    
    @OneToMany(mappedBy = "leaderboard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkoutResultEntity> workoutResults;
} 