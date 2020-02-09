CREATE TABLE biosample_experiment
(
        biosample_accession VARCHAR(255) REFERENCES biosample(accession),
        experiment_accession VARCHAR(255) REFERENCES experiment(accession),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_biosample_experiment_01 ON biosample_experiment
(biosample_accession);
CREATE INDEX idx_biosample_experiment_02 ON biosample_experiment
(experiment_accession);
