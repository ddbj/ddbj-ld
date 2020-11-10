CREATE TABLE study_submission
(
        study_accession VARCHAR(255) REFERENCES study(accession),
        submission_accession VARCHAR(255) REFERENCES submission(accession),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_study_submission_01 ON study_submission
(study_accession);
CREATE INDEX idx_study_submission_02 ON study_submission
(submission_accession);
