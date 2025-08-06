-- Adicionar coluna leaderboard_id na tabela workouts
ALTER TABLE workouts ADD COLUMN leaderboard_id BIGINT;

-- Adicionar foreign key
ALTER TABLE workouts ADD CONSTRAINT fk_workouts_leaderboard 
    FOREIGN KEY (leaderboard_id) REFERENCES leaderboard(id) ON DELETE CASCADE; 