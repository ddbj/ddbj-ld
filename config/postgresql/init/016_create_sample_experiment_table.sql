CREATE TABLE sample_experiment
(       sample_accession          VARCHAR(22)     REFERENCES sample(accession),
        experiment_accession      VARCHAR(22)     REFERENCES experiment(accession),
        created_at                TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
        updated_at                TIMESTAMP       DEFAULT CURRENT_TIMESTAMP);
