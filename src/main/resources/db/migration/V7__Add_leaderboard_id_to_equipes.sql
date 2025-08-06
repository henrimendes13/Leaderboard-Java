-- Adicionar coluna leaderboard_id na tabela equipes
ALTER TABLE equipes ADD COLUMN leaderboard_id BIGINT;

-- Adicionar foreign key
ALTER TABLE equipes ADD CONSTRAINT fk_equipes_leaderboard 
    FOREIGN KEY (leaderboard_id) REFERENCES leaderboard(id) ON DELETE CASCADE; 