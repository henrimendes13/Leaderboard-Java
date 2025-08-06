package com.leaderboard.service;

import com.leaderboard.Entities.EquipeEntity;
import com.leaderboard.dto.EquipeDTO;
import com.leaderboard.repository.EquipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipeService {
    
    private final EquipeRepository equipeRepository;
    
    // Converter Entity para DTO
    private EquipeDTO convertToDTO(EquipeEntity entity) {
        return new EquipeDTO(
            entity.getId(),
            entity.getNome(),
            entity.getDescricao(),
            entity.getDataCriacao(),
            entity.getDataAtualizacao()
        );
    }
    
    // Converter DTO para Entity
    private EquipeEntity convertToEntity(EquipeDTO dto) {
        EquipeEntity entity = new EquipeEntity();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        entity.setDataCriacao(dto.getDataCriacao());
        entity.setDataAtualizacao(dto.getDataAtualizacao());
        return entity;
    }
    
    // Criar nova equipe
    public EquipeDTO criarEquipe(EquipeDTO equipeDTO) {
        // Verificar se já existe equipe com o mesmo nome
        if (equipeRepository.existsByNome(equipeDTO.getNome())) {
            throw new RuntimeException("Já existe uma equipe com o nome: " + equipeDTO.getNome());
        }
        
        EquipeEntity entity = convertToEntity(equipeDTO);
        entity.setDataCriacao(LocalDateTime.now());
        entity.setDataAtualizacao(LocalDateTime.now());
        
        EquipeEntity savedEntity = equipeRepository.save(entity);
        return convertToDTO(savedEntity);
    }
    
    // Buscar equipe por ID
    public Optional<EquipeDTO> buscarPorId(Long id) {
        Optional<EquipeEntity> entity = equipeRepository.findById(id);
        return entity.map(this::convertToDTO);
    }
    
    // Buscar equipe por nome
    public Optional<EquipeDTO> buscarPorNome(String nome) {
        Optional<EquipeEntity> entity = equipeRepository.findByNome(nome);
        return entity.map(this::convertToDTO);
    }
    
    // Listar todas as equipes
    public List<EquipeDTO> listarTodas() {
        return equipeRepository.findAllByOrderByDataCriacaoDesc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Buscar equipes por nome (contendo)
    public List<EquipeDTO> buscarPorNomeContendo(String nome) {
        return equipeRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // Atualizar equipe
    public Optional<EquipeDTO> atualizarEquipe(Long id, EquipeDTO equipeDTO) {
        Optional<EquipeEntity> existingEntity = equipeRepository.findById(id);
        
        if (existingEntity.isPresent()) {
            EquipeEntity entity = existingEntity.get();
            
            // Verificar se o novo nome já existe em outra equipe
            if (!entity.getNome().equals(equipeDTO.getNome()) && 
                equipeRepository.existsByNome(equipeDTO.getNome())) {
                throw new RuntimeException("Já existe uma equipe com o nome: " + equipeDTO.getNome());
            }
            
            entity.setNome(equipeDTO.getNome());
            entity.setDescricao(equipeDTO.getDescricao());
            entity.setDataAtualizacao(LocalDateTime.now());
            
            EquipeEntity savedEntity = equipeRepository.save(entity);
            return Optional.of(convertToDTO(savedEntity));
        }
        
        return Optional.empty();
    }
    
    // Deletar equipe
    public boolean deletarEquipe(Long id) {
        if (equipeRepository.existsById(id)) {
            equipeRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Verificar se equipe existe
    public boolean equipeExiste(Long id) {
        return equipeRepository.existsById(id);
    }
    
    // Verificar se nome de equipe existe
    public boolean nomeEquipeExiste(String nome) {
        return equipeRepository.existsByNome(nome);
    }
} 