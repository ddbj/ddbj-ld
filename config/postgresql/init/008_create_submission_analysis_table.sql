CREATE TABLE submission_analysis
(
        submission_accession VARCHAR(22) REFERENCES submission(accession),
        analysis_accession VARCHAR(22) REFERENCES analysis(accession),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
