package com.leaderboard.dto;

import com.leaderboard.Entities.enums.TipoWorkout;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutDTO {
    
    private Long id;
    private String nome;
    private String descricao;
    private TipoWorkout tipo;
    private String unidade;
    private Boolean ativo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    
    // Construtor customizado
    public WorkoutDTO(String nome, String descricao, TipoWorkout tipo, String unidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
        this.unidade = unidade;
        this.ativo = true;
    }
} 