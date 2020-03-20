CREATE TABLE jga_relation
(
  self_accession VARCHAR(255),
  parent_accession VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(self_accession, parent_accession)
);

CREATE INDEX idx_jga_relation_01 ON jga_relation
(self_accession);
CREATE INDEX idx_jga_relation_02 ON jga_relation
(parent_accession);
