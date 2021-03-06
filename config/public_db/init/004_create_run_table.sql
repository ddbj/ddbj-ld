CREATE TABLE run
(
        accession VARCHAR(255) NOT NULL,
        status VARCHAR(255),
        updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        published TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        received TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        visibility VARCHAR(255),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (accession)
);
