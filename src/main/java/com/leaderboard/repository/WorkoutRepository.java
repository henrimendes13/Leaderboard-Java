package com.leaderboard.repository;

import com.leaderboard.Entities.WorkoutEntity;
import com.leaderboard.Entities.enums.TipoWorkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutRepository extends JpaRepository<WorkoutEntity, Long> {
    
    // Buscar por nome
    Optional<WorkoutEntity> findByNome(String nome);
    
    // Buscar por tipo
    List<WorkoutEntity> findByTipo(TipoWorkout tipo);
    
    // Buscar apenas ativos
    List<WorkoutEntity> findByAtivoTrue();
    
    // Buscar por tipo e ativo
    List<WorkoutEntity> findByTipoAndAtivoTrue(TipoWorkout tipo);
    
    // Verificar se existe por nome
    boolean existsByNome(String nome);
    
    // Verificar se existe por nome ignorando o ID atual (para updates)
    @Query("SELECT COUNT(w) > 0 FROM WorkoutEntity w WHERE w.nome = :nome AND w.id != :id")
    boolean existsByNomeAndIdNot(@Param("nome") String nome, @Param("id") Long id);
    
    // Buscar por nome contendo (case insensitive)
    @Query("SELECT w FROM WorkoutEntity w WHERE LOWER(w.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND w.ativo = true")
    List<WorkoutEntity> findByNomeContainingIgnoreCase(@Param("nome") String nome);
    
    // Contar workouts ativos
    long countByAtivoTrue();
    
    // Contar por tipo
    long countByTipo(TipoWorkout tipo);
} 