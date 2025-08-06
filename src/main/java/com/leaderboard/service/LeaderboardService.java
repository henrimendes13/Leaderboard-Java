package com.leaderboard.service;

import com.leaderboard.Entities.LeaderboardEntity;
import com.leaderboard.dto.LeaderboardDTO;
import com.leaderboard.repository.LeaderboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaderboardService {
    
    private final LeaderboardRepository leaderboardRepository;
    
    // Criar novo leaderboard
    public LeaderboardDTO criarLeaderboard(LeaderboardDTO leaderboardDTO) {
        LeaderboardEntity entity = new LeaderboardEntity();
        // A entidade agora é apenas um container para equipes, workouts e resultados
        LeaderboardEntity savedEntity = leaderboardRepository.save(entity);
        return convertToDTO(savedEntity);
    }
    
    // Buscar leaderboard por ID
    public Optional<LeaderboardDTO> buscarPorId(Long id) {
        return leaderboardRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    // Listar todos os leaderboards
    public List<LeaderboardDTO> listarTodos() {
        return leaderboardRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Atualizar leaderboard
    public Optional<LeaderboardDTO> atualizarLeaderboard(Long id, LeaderboardDTO leaderboardDTO) {
        Optional<LeaderboardEntity> existingEntity = leaderboardRepository.findById(id);
        
        if (existingEntity.isPresent()) {
            LeaderboardEntity entity = existingEntity.get();
            // A entidade agora é apenas um container, não há campos específicos para atualizar
            LeaderboardEntity savedEntity = leaderboardRepository.save(entity);
            return Optional.of(convertToDTO(savedEntity));
        }
        
        return Optional.empty();
    }
    
    // Deletar leaderboard
    public boolean deletarLeaderboard(Long id) {
        if (leaderboardRepository.existsById(id)) {
            leaderboardRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Converter Entity para DTO
    private LeaderboardDTO convertToDTO(LeaderboardEntity entity) {
        LeaderboardDTO dto = new LeaderboardDTO();
        dto.setId(entity.getId());
        // Como a entidade não tem mais playerName e score, vamos usar valores padrão
        dto.setPlayerName("Leaderboard " + entity.getId());
        dto.setScore(0);
        return dto;
    }
} 