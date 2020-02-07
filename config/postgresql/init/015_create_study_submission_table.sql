CREATE TABLE study_submission
(
        study_accession VARCHAR(255) REFERENCES study(accession),
        submission_accession VARCHAR(255) REFERENCES submission(accession),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
