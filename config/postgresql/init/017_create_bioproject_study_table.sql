CREATE TABLE bioproject_study
(
  bioproject_accession VARCHAR(255) REFERENCES bioproject(accession),
  study_accession VARCHAR(255) REFERENCES study(accession),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
