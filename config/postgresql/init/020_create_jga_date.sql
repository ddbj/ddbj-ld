CREATE TABLE jga_date
(
  accession VARCHAR(255),
  date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  date_published TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  date_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(accession)
);

CREATE INDEX idx_jga_date_01 ON jga_date
(accession);
