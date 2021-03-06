CREATE TABLE experiment_run
(
        experiment_accession VARCHAR(255) REFERENCES experiment(accession),
        run_accession VARCHAR(255) REFERENCES run(accession),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_experiment_run_01 ON experiment_run
(experiment_accession);
CREATE INDEX idx_experiment_run_02 ON experiment_run
(run_accession);
