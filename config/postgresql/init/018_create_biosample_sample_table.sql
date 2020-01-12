CREATE TABLE biosample_sample
(
  biosample_accession VARCHAR(22) REFERENCES biosample(accession),
  sample_accession VARCHAR(22) REFERENCES sample(accession),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
