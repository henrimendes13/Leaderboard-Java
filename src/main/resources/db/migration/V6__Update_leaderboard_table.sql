-- Remover a tabela leaderboard antiga e criar a nova estrutura
DROP TABLE IF EXISTS leaderboard CASCADE;

CREATE TABLE leaderboard (
    id BIGSERIAL PRIMARY KEY
); 