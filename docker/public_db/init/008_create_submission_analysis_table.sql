CREATE TABLE submission_analysis
(
        submission_accession VARCHAR(255) REFERENCES submission(accession),
        analysis_accession VARCHAR(255) REFERENCES analysis(accession),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_submission_analysis_01 ON submission_analysis
(submission_accession);
CREATE INDEX idx_submission_analysis_02 ON submission_analysis
(analysis_accession);
