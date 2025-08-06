package com.leaderboard.Entities.enums;

public enum TipoWorkout {
    REPETICOES("repetitions", "Repetições"),
    TEMPO("time", "Tempo"),
    PESO("weight", "Peso");

    private final String codigo;
    private final String descricao;

    TipoWorkout(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoWorkout fromCodigo(String codigo) {
        for (TipoWorkout tipo : values()) {
            if (tipo.codigo.equals(codigo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código de tipo inválido: " + codigo);
    }
} 