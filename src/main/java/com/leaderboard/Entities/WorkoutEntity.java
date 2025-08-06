package com.leaderboard.Entities;

import com.leaderboard.Entities.enums.TipoWorkout;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "workouts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;
    
    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoWorkout tipo;
    
    @Column(name = "unidade", nullable = false, length = 50)
    private String unidade;
    
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;
    
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;
    
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leaderboard_id")
    private LeaderboardEntity leaderboard;
    
    // Construtor customizado
    public WorkoutEntity(String nome, String descricao, TipoWorkout tipo, String unidade) {
        this();
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
        this.unidade = unidade;
    }
    
    // Métodos de negócio
    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
    }
    
    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
} 