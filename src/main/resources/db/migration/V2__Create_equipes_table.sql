CREATE TABLE IF NOT EXISTS equipes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL UNIQUE,
    descricao VARCHAR(500),
    data_criacao TIMESTAMP,
    data_atualizacao TIMESTAMP
);

-- Criar índices para melhor performance
CREATE INDEX idx_equipes_nome ON equipes(nome);
CREATE INDEX idx_equipes_data_criacao ON equipes(data_criacao);
