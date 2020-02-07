CREATE TABLE bioproject_submission
(       bioproject_accession      VARCHAR(255)     REFERENCES bioproject(accession),
        submission_accession      VARCHAR(255)     REFERENCES submission(accession),
        created_at                TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
        updated_at                TIMESTAMP       DEFAULT CURRENT_TIMESTAMP);
