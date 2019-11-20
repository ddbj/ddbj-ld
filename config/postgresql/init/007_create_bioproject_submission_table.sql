CREATE TABLE bioproject_submission
(       bioproject_accession      VARCHAR(22)     REFERENCES bioproject(accession),
        submission_accession      VARCHAR(22)     REFERENCES submission(accession),
        created_at                TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
        updated_at                TIMESTAMP       DEFAULT CURRENT_TIMESTAMP);
