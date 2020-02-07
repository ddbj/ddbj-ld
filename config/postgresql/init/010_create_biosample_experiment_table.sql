CREATE TABLE biosample_experiment
(
        biosample_accession VARCHAR(255) REFERENCES biosample(accession),
        experiment_accession VARCHAR(255) REFERENCES experiment(accession),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
