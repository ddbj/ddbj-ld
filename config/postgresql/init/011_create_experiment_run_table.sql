CREATE TABLE experiment_run
(       experiment_accession      VARCHAR(22)     REFERENCES experiment(accession),
        run_accession             VARCHAR(22)     REFERENCES run(accession),
        created_at                TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
        updated_at                TIMESTAMP       DEFAULT CURRENT_TIMESTAMP);
