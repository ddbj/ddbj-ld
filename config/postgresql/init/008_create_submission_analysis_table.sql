CREATE TABLE submission_analysis
(
        submission_accession VARCHAR(255) REFERENCES submission(accession),
        analysis_accession VARCHAR(255) REFERENCES analysis(accession),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
