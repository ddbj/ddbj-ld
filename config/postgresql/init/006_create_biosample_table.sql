CREATE TABLE biosample
(       accession       VARCHAR(22)     NOT NULL,
        created_at      TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
        updated_at      TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (accession));
