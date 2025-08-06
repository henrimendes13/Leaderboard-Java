package com.leaderboard.repository;

import com.leaderboard.Entities.WorkoutResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutResultRepository extends JpaRepository<WorkoutResultEntity, Long> {
    
    // Buscar por equipe
    List<WorkoutResultEntity> findByEquipeId(Long equipeId);
    
    // Buscar por workout
    List<WorkoutResultEntity> findByWorkoutId(Long workoutId);
    
    // Buscar por equipe e workout
    Optional<WorkoutResultEntity> findByEquipeIdAndWorkoutId(Long equipeId, Long workoutId);
    
    // Verificar se existe resultado para equipe e workout
    boolean existsByEquipeIdAndWorkoutId(Long equipeId, Long workoutId);
    
    // Buscar todos os resultados ordenados por equipe e workout
    @Query("SELECT wr FROM WorkoutResultEntity wr ORDER BY wr.equipe.id, wr.workout.id")
    List<WorkoutResultEntity> findAllOrdered();
    
    // Contar resultados por equipe
    long countByEquipeId(Long equipeId);
    
    // Contar resultados por workout
    long countByWorkoutId(Long workoutId);
} 