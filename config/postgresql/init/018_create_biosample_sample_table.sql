CREATE TABLE biosample_sample
(
  biosample_accession VARCHAR(255) REFERENCES biosample(accession),
  sample_accession VARCHAR(255) REFERENCES sample(accession),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_biosample_sample_01 ON biosample_sample
(biosample_accession);
CREATE INDEX idx_biosample_sample_02 ON biosample_sample
(sample_accession);
