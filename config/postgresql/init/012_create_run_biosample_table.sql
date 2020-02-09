CREATE TABLE run_biosample
(
        run_accession VARCHAR(255) REFERENCES run(accession),
        biosample_accession VARCHAR(255) REFERENCES biosample(accession),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_run_biosample_01 ON run_biosample
(run_accession);
CREATE INDEX idx_run_biosample_02 ON run_biosample
(biosample_accession);
