package com.leaderboard.service;

import com.leaderboard.Entities.WorkoutEntity;
import com.leaderboard.Entities.enums.TipoWorkout;
import com.leaderboard.dto.WorkoutDTO;
import com.leaderboard.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutService {
    
    private final WorkoutRepository workoutRepository;
    
    // Criar novo workout
    public WorkoutDTO criarWorkout(WorkoutDTO workoutDTO) {
        // Validar se o nome já existe
        if (workoutRepository.existsByNome(workoutDTO.getNome())) {
            throw new RuntimeException("Já existe um workout com o nome: " + workoutDTO.getNome());
        }
        
        // Converter DTO para Entity
        WorkoutEntity workoutEntity = new WorkoutEntity();
        workoutEntity.setNome(workoutDTO.getNome());
        workoutEntity.setDescricao(workoutDTO.getDescricao());
        workoutEntity.setTipo(workoutDTO.getTipo());
        workoutEntity.setUnidade(workoutDTO.getUnidade());
        workoutEntity.setAtivo(true);
        workoutEntity.setDataCriacao(LocalDateTime.now());
        
        // Salvar no banco
        WorkoutEntity savedEntity = workoutRepository.save(workoutEntity);
        
        // Converter de volta para DTO
        return converterParaDTO(savedEntity);
    }
    
    // Buscar workout por ID
    public Optional<WorkoutDTO> buscarPorId(Long id) {
        return workoutRepository.findById(id)
                .map(this::converterParaDTO);
    }
    
    // Buscar workout por nome
    public Optional<WorkoutDTO> buscarPorNome(String nome) {
        return workoutRepository.findByNome(nome)
                .map(this::converterParaDTO);
    }
    
    // Listar todos os workouts ativos
    public List<WorkoutDTO> listarTodosAtivos() {
        return workoutRepository.findByAtivoTrue()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    // Listar todos os workouts (incluindo inativos)
    public List<WorkoutDTO> listarTodos() {
        return workoutRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    // Listar workouts por tipo
    public List<WorkoutDTO> listarPorTipo(TipoWorkout tipo) {
        return workoutRepository.findByTipoAndAtivoTrue(tipo)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    // Buscar workouts por nome (busca parcial)
    public List<WorkoutDTO> buscarPorNomeContendo(String nome) {
        return workoutRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
    
    // Atualizar workout
    public Optional<WorkoutDTO> atualizarWorkout(Long id, WorkoutDTO workoutDTO) {
        Optional<WorkoutEntity> optionalEntity = workoutRepository.findById(id);
        
        if (optionalEntity.isPresent()) {
            WorkoutEntity entity = optionalEntity.get();
            
            // Verificar se o nome já existe (ignorando o próprio registro)
            if (!entity.getNome().equals(workoutDTO.getNome()) && 
                workoutRepository.existsByNomeAndIdNot(workoutDTO.getNome(), id)) {
                throw new RuntimeException("Já existe um workout com o nome: " + workoutDTO.getNome());
            }
            
            // Atualizar campos
            entity.setNome(workoutDTO.getNome());
            entity.setDescricao(workoutDTO.getDescricao());
            entity.setTipo(workoutDTO.getTipo());
            entity.setUnidade(workoutDTO.getUnidade());
            entity.setDataAtualizacao(LocalDateTime.now());
            
            // Salvar no banco
            WorkoutEntity savedEntity = workoutRepository.save(entity);
            
            return Optional.of(converterParaDTO(savedEntity));
        }
        
        return Optional.empty();
    }
    
    // Deletar workout (soft delete)
    public boolean deletarWorkout(Long id) {
        Optional<WorkoutEntity> optionalEntity = workoutRepository.findById(id);
        
        if (optionalEntity.isPresent()) {
            WorkoutEntity entity = optionalEntity.get();
            entity.setAtivo(false);
            entity.setDataAtualizacao(LocalDateTime.now());
            workoutRepository.save(entity);
            return true;
        }
        
        return false;
    }
    
    // Ativar workout
    public boolean ativarWorkout(Long id) {
        Optional<WorkoutEntity> optionalEntity = workoutRepository.findById(id);
        
        if (optionalEntity.isPresent()) {
            WorkoutEntity entity = optionalEntity.get();
            entity.setAtivo(true);
            entity.setDataAtualizacao(LocalDateTime.now());
            workoutRepository.save(entity);
            return true;
        }
        
        return false;
    }
    
    // Contar workouts ativos
    public long contarAtivos() {
        return workoutRepository.countByAtivoTrue();
    }
    
    // Contar workouts por tipo
    public long contarPorTipo(TipoWorkout tipo) {
        return workoutRepository.countByTipo(tipo);
    }
    
    // Converter Entity para DTO
    private WorkoutDTO converterParaDTO(WorkoutEntity entity) {
        return new WorkoutDTO(
            entity.getId(),
            entity.getNome(),
            entity.getDescricao(),
            entity.getTipo(),
            entity.getUnidade(),
            entity.getAtivo(),
            entity.getDataCriacao(),
            entity.getDataAtualizacao()
        );
    }
} 