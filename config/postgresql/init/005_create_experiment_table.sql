CREATE TABLE experiment
(
        accession VARCHAR(22) NOT NULL,
        status VARCHAR(12),
        updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        published TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        received TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        visibility VARCHAR(12),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (accession)
);
