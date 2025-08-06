CREATE TABLE workouts (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    tipo VARCHAR(20) NOT NULL,
    unidade VARCHAR(50) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP,
    
    CONSTRAINT uk_workout_nome UNIQUE (nome)
);

-- Índices para melhor performance
CREATE INDEX idx_workout_tipo ON workouts(tipo);
CREATE INDEX idx_workout_ativo ON workouts(ativo);
CREATE INDEX idx_workout_data_criacao ON workouts(data_criacao); 