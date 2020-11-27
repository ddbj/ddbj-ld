CREATE TABLE analysis
(
        accession VARCHAR(255) NOT NULL,
        status VARCHAR(255),
        updated TIMESTAMP,
        published TIMESTAMP,
        received TIMESTAMP,
        visibility VARCHAR(255),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY
(accession)
);
