CREATE TABLE workout_results (
    id BIGSERIAL PRIMARY KEY,
    equipe_id BIGINT NOT NULL,
    workout_id BIGINT NOT NULL,
    result VARCHAR(255) NOT NULL,
    position INTEGER,
    points INTEGER,
    leaderboard_id BIGINT,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_workout_results_equipe FOREIGN KEY (equipe_id) REFERENCES equipes(id) ON DELETE CASCADE,
    CONSTRAINT fk_workout_results_workout FOREIGN KEY (workout_id) REFERENCES workouts(id) ON DELETE CASCADE,
    
    CONSTRAINT unique_equipe_workout UNIQUE (equipe_id, workout_id)
); 