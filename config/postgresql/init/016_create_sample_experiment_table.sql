CREATE TABLE sample_experiment
(
        sample_accession VARCHAR(255) REFERENCES sample(accession),
        experiment_accession VARCHAR(255) REFERENCES experiment(accession),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_sample_experiment_01 ON sample_experiment
(sample_accession);
CREATE INDEX idx_sample_experiment_02 ON sample_experiment
(experiment_accession);
