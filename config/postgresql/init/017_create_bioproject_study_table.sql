CREATE TABLE bioproject_study
(
  bioproject_accession VARCHAR(22) REFERENCES bioproject(accession),
  study_accession VARCHAR(22) REFERENCES study(accession),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
