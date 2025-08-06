package com.leaderboard.repository;

import com.leaderboard.Entities.EquipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipeRepository extends JpaRepository<EquipeEntity, Long> {
    
    // Buscar equipe por nome
    Optional<EquipeEntity> findByNome(String nome);
    
    // Buscar equipes que contenham o nome (case insensitive)
    @Query("SELECT e FROM EquipeEntity e WHERE LOWER(e.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<EquipeEntity> findByNomeContainingIgnoreCase(@Param("nome") String nome);
    
    // Verificar se existe equipe com o nome
    boolean existsByNome(String nome);
    
    // Buscar equipes ordenadas por data de criação (mais recentes primeiro)
    List<EquipeEntity> findAllByOrderByDataCriacaoDesc();
}