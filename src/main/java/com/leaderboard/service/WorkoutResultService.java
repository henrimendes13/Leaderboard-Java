package com.leaderboard.service;

import com.leaderboard.Entities.EquipeEntity;
import com.leaderboard.Entities.WorkoutEntity;
import com.leaderboard.Entities.WorkoutResultEntity;
import com.leaderboard.dto.WorkoutResultDTO;
import com.leaderboard.repository.EquipeRepository;
import com.leaderboard.repository.WorkoutRepository;
import com.leaderboard.repository.WorkoutResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutResultService {
    
    private final WorkoutResultRepository workoutResultRepository;
    private final EquipeRepository equipeRepository;
    private final WorkoutRepository workoutRepository;
    
    // Criar novo resultado
    public WorkoutResultDTO criarResultado(WorkoutResultDTO resultDTO) {
        // Validar se equipe e workout existem
        EquipeEntity equipe = equipeRepository.findById(resultDTO.getEquipeId())
                .orElseThrow(() -> new RuntimeException("Equipe não encontrada"));
        
        WorkoutEntity workout = workoutRepository.findById(resultDTO.getWorkoutId())
                .orElseThrow(() -> new RuntimeException("Workout não encontrado"));
        
        // Verificar se já existe resultado para esta equipe e workout
        if (workoutResultRepository.existsByEquipeIdAndWorkoutId(resultDTO.getEquipeId(), resultDTO.getWorkoutId())) {
            throw new RuntimeException("Já existe um resultado para esta equipe neste workout");
        }
        
        // Criar entidade
        WorkoutResultEntity resultEntity = new WorkoutResultEntity();
        resultEntity.setEquipe(equipe);
        resultEntity.setWorkout(workout);
        resultEntity.setResult(resultDTO.getResult());
        resultEntity.setDataCriacao(LocalDateTime.now());
        resultEntity.setDataAtualizacao(LocalDateTime.now());
        
        // Salvar no banco
        WorkoutResultEntity savedEntity = workoutResultRepository.save(resultEntity);
        
        // Converter de volta para DTO
        return converterParaDTO(savedEntity);
    }
    
    // Buscar resultado por ID
    public Optional<WorkoutResultDTO> buscarPorId(Long id) {
        return workoutResultRepository.findById(id)
                .map(this::converterParaDTO);
    }
    
    // Listar todos os resultados
    public List<WorkoutResultDTO> listarTodos() {
        return workoutResultRepository.findAllOrdered()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    // Atualizar resultado
    public WorkoutResultDTO atualizarResultado(Long id, WorkoutResultDTO resultDTO) {
        WorkoutResultEntity existingEntity = workoutResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resultado não encontrado"));
        
        // Atualizar dados
        existingEntity.setResult(resultDTO.getResult());
        existingEntity.setDataAtualizacao(LocalDateTime.now());
        
        // Salvar no banco
        WorkoutResultEntity updatedEntity = workoutResultRepository.save(existingEntity);
        
        // Converter de volta para DTO
        return converterParaDTO(updatedEntity);
    }
    
    // Deletar resultado
    public void deletarResultado(Long id) {
        if (!workoutResultRepository.existsById(id)) {
            throw new RuntimeException("Resultado não encontrado");
        }
        workoutResultRepository.deleteById(id);
    }
    
    // Buscar resultados por equipe
    public List<WorkoutResultDTO> buscarPorEquipe(Long equipeId) {
        return workoutResultRepository.findByEquipeId(equipeId)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    // Buscar resultados por workout
    public List<WorkoutResultDTO> buscarPorWorkout(Long workoutId) {
        return workoutResultRepository.findByWorkoutId(workoutId)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    // Buscar resultado específico por equipe e workout
    public Optional<WorkoutResultDTO> buscarPorEquipeEWorkout(Long equipeId, Long workoutId) {
        return workoutResultRepository.findByEquipeIdAndWorkoutId(equipeId, workoutId)
                .map(this::converterParaDTO);
    }
    
    // Dados do leaderboard
    public List<Object> getLeaderboardData() {
        List<EquipeEntity> equipes = equipeRepository.findAll();
        List<WorkoutEntity> workouts = workoutRepository.findByAtivoTrue();
        List<WorkoutResultEntity> results = workoutResultRepository.findAll();
        
        List<Object> leaderboardData = new ArrayList<>();
        
        for (EquipeEntity equipe : equipes) {
            Map<String, Object> equipeData = new HashMap<>();
            equipeData.put("equipeId", equipe.getId());
            equipeData.put("equipeNome", equipe.getNome());
            
            Map<String, Object> workoutResults = new HashMap<>();
            int totalPoints = 0;
            
            for (WorkoutEntity workout : workouts) {
                WorkoutResultEntity result = results.stream()
                        .filter(r -> r.getEquipe().getId().equals(equipe.getId()) && 
                                   r.getWorkout().getId().equals(workout.getId()))
                        .findFirst()
                        .orElse(null);
                
                Map<String, Object> workoutData = new HashMap<>();
                if (result != null) {
                    workoutData.put("result", result.getResult());
                    workoutData.put("position", result.getPosition());
                    workoutData.put("points", result.getPoints());
                } else {
                    workoutData.put("result", "");
                    workoutData.put("position", 0);
                    workoutData.put("points", 0);
                }
                
                workoutResults.put("workout_" + workout.getId(), workoutData);
            }
            
            equipeData.put("workoutResults", workoutResults);
            equipeData.put("totalPoints", totalPoints);
            leaderboardData.add(equipeData);
        }
        
        return leaderboardData;
    }
    
    // Converter Entity para DTO
    private WorkoutResultDTO converterParaDTO(WorkoutResultEntity entity) {
        WorkoutResultDTO dto = new WorkoutResultDTO();
        dto.setId(entity.getId());
        dto.setEquipeId(entity.getEquipe().getId());
        dto.setEquipeNome(entity.getEquipe().getNome());
        dto.setWorkoutId(entity.getWorkout().getId());
        dto.setWorkoutNome(entity.getWorkout().getNome());
        dto.setResult(entity.getResult());
        dto.setPosition(entity.getPosition());
        dto.setPoints(entity.getPoints());
        dto.setDataCriacao(entity.getDataCriacao());
        dto.setDataAtualizacao(entity.getDataAtualizacao());
        return dto;
    }
} 