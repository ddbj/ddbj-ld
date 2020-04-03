CREATE TABLE bioproject_submission
(
        bioproject_accession VARCHAR(255) REFERENCES bioproject(accession),
        submission_accession VARCHAR(255) REFERENCES submission(accession),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_bioproject_submission_01 ON bioproject_submission
(bioproject_accession);
CREATE INDEX idx_bioproject_submission_02 ON bioproject_submission
(submission_accession);
