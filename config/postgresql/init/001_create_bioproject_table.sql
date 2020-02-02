CREATE TABLE bioproject
(
        accession VARCHAR(22) NOT NULL,
        status VARCHAR (12),
        updated TIMESTAMP,
        published TIMESTAMP,
        received TIMESTAMP,
        visibility VARCHAR(12),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (accession)
);
