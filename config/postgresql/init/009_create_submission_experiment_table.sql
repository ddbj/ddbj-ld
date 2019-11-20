CREATE TABLE submission_experiment
(       submission_accession      VARCHAR(22)     REFERENCES submission(accession),
        experiment_accession      VARCHAR(22)     REFERENCES experiment(accession),
        created_at                TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
        updated_at                TIMESTAMP       DEFAULT CURRENT_TIMESTAMP);
