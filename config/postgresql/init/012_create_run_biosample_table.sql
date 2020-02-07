CREATE TABLE run_biosample
(       run_accession             VARCHAR(255)     REFERENCES run(accession),
        biosample_accession       VARCHAR(255)     REFERENCES biosample(accession),
        created_at                TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
        updated_at                TIMESTAMP       DEFAULT CURRENT_TIMESTAMP);
