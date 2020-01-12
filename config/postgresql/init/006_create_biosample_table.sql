CREATE TABLE biosample
(
        accession VARCHAR(22) NOT NULL,
        updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        published TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        received TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        visibility VARCHAR(12),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (accession)
);
