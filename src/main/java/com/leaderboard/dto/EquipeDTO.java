package com.leaderboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipeDTO {
    private Long id;
    private String nome;
    private String descricao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    
    // Construtor customizado
    public EquipeDTO(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }
} 