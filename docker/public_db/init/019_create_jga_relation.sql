CREATE TABLE jga_relation
(
  self_accession VARCHAR(255),
  parent_accession VARCHAR(255),
  self_type VARCHAR(255),
  parent_type VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(self_accession, parent_accession)
);

CREATE INDEX idx_jga_relation_01 ON jga_relation
(self_accession, parent_type, parent_accession);
CREATE INDEX idx_jga_relation_02 ON jga_relation
(parent_accession, self_type, self_accession);
CREATE INDEX idx_jga_relation_03 ON jga_relation
(self_accession, parent_type);
CREATE INDEX idx_jga_relation_04 ON jga_relation
(parent_accession, self_type);
