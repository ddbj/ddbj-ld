CREATE TABLE submission_experiment
(
        submission_accession VARCHAR(255) REFERENCES submission(accession),
        experiment_accession VARCHAR(255) REFERENCES experiment(accession),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_submission_experiment_01 ON submission_experiment
(submission_accession);
CREATE INDEX idx_submission_experiment_02 ON submission_experiment
(experiment_accession);
