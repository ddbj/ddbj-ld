CREATE TABLE run_biosample
(       run_accession             VARCHAR(22)     REFERENCES run(accession),
        biosample_accession       VARCHAR(22)     REFERENCES biosample(accession),
        created_at                TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
        updated_at                TIMESTAMP       DEFAULT CURRENT_TIMESTAMP);
