package com.leaderboard.controller;

import com.leaderboard.Entities.WorkoutResultEntity;
import com.leaderboard.dto.WorkoutResultDTO;
import com.leaderboard.service.WorkoutResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout-results")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
@Tag(name = "Workout Results", description = "API para gerenciamento de resultados de workouts")
@RequiredArgsConstructor
public class WorkoutResultController {
    
    private final WorkoutResultService workoutResultService;
    
    @Operation(summary = "Listar todos os resultados", description = "Retorna todos os resultados de workouts")
    @GetMapping
    public ResponseEntity<List<WorkoutResultDTO>> getAllResults() {
        List<WorkoutResultDTO> results = workoutResultService.listarTodos();
        return ResponseEntity.ok(results);
    }
    
    @Operation(summary = "Buscar resultado por ID", description = "Retorna um resultado específico por ID")
    @GetMapping("/{id}")
    public ResponseEntity<WorkoutResultDTO> getResultById(@PathVariable Long id) {
        return workoutResultService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Criar novo resultado", description = "Cria um novo resultado de workout")
    @PostMapping
    public ResponseEntity<WorkoutResultDTO> createResult(@RequestBody WorkoutResultDTO resultDTO) {
        try {
            WorkoutResultDTO createdResult = workoutResultService.criarResultado(resultDTO);
            return ResponseEntity.ok(createdResult);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Operation(summary = "Atualizar resultado", description = "Atualiza um resultado existente")
    @PutMapping("/{id}")
    public ResponseEntity<WorkoutResultDTO> updateResult(@PathVariable Long id, @RequestBody WorkoutResultDTO resultDTO) {
        try {
            WorkoutResultDTO updatedResult = workoutResultService.atualizarResultado(id, resultDTO);
            return ResponseEntity.ok(updatedResult);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Operation(summary = "Deletar resultado", description = "Remove um resultado específico")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResult(@PathVariable Long id) {
        try {
            workoutResultService.deletarResultado(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @Operation(summary = "Buscar resultados por equipe", description = "Retorna todos os resultados de uma equipe específica")
    @GetMapping("/equipe/{equipeId}")
    public ResponseEntity<List<WorkoutResultDTO>> getResultsByEquipe(@PathVariable Long equipeId) {
        List<WorkoutResultDTO> results = workoutResultService.buscarPorEquipe(equipeId);
        return ResponseEntity.ok(results);
    }
    
    @Operation(summary = "Buscar resultados por workout", description = "Retorna todos os resultados de um workout específico")
    @GetMapping("/workout/{workoutId}")
    public ResponseEntity<List<WorkoutResultDTO>> getResultsByWorkout(@PathVariable Long workoutId) {
        List<WorkoutResultDTO> results = workoutResultService.buscarPorWorkout(workoutId);
        return ResponseEntity.ok(results);
    }
    
    @Operation(summary = "Dados do leaderboard", description = "Retorna dados formatados para o leaderboard")
    @GetMapping("/leaderboard")
    public ResponseEntity<List<Object>> getLeaderboardData() {
        List<Object> leaderboardData = workoutResultService.getLeaderboardData();
        return ResponseEntity.ok(leaderboardData);
    }
} 