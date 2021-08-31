CREATE INDEX idx_jga_analysis_study_01 ON t_jga_analysis_study (analysis_accession);
CREATE INDEX idx_jga_analysis_study_02 ON t_jga_analysis_study (study_accession);
CREATE INDEX idx_jga_data_experiment_01 ON t_jga_data_experiment (data_accession);
CREATE INDEX idx_jga_data_experiment_02 ON t_jga_data_experiment (experiment_accession);
CREATE INDEX idx_jga_dataset_analysis_01 ON t_jga_dataset_analysis (dataset_accession);
CREATE INDEX idx_jga_dataset_analysis_02 ON t_jga_dataset_analysis (analysis_accession);
CREATE INDEX idx_jga_dataset_data_01 ON t_jga_dataset_data (dataset_accession);
CREATE INDEX idx_jga_dataset_data_02 ON t_jga_dataset_data (data_accession);
CREATE INDEX idx_jga_dataset_policy_01 ON t_jga_dataset_policy (dataset_accession);
CREATE INDEX idx_jga_dataset_policy_02 ON t_jga_dataset_policy (policy_accession);
CREATE INDEX idx_jga_experiment_study_01 ON t_jga_experiment_study (experiment_accession);
CREATE INDEX idx_jga_experiment_study_02 ON t_jga_experiment_study (study_accession);
CREATE INDEX idx_jga_policy_dac_01 ON t_jga_policy_dac (policy_accession);
CREATE INDEX idx_jga_policy_dac_02 ON t_jga_policy_dac (dac_accession);
CREATE INDEX idx_jga_date_01 ON t_jga_date (accession);

CREATE INDEX idx_dra_submission_01 ON t_dra_submission (accession);
CREATE INDEX idx_dra_submission_02 ON t_dra_submission (submission);
CREATE INDEX idx_dra_submission_03 ON t_dra_submission (status);
CREATE INDEX idx_dra_submission_04 ON t_dra_submission (updated);
CREATE INDEX idx_dra_submission_05 ON t_dra_submission (visibility);
CREATE INDEX idx_dra_submission_06 ON t_dra_submission (experiment);
CREATE INDEX idx_dra_submission_07 ON t_dra_submission (sample);
CREATE INDEX idx_dra_submission_08 ON t_dra_submission (study);
CREATE INDEX idx_dra_submission_09 ON t_dra_submission (biosample);
CREATE INDEX idx_dra_submission_10 ON t_dra_submission (bioproject);
CREATE INDEX idx_dra_experiment_01 ON t_dra_experiment (accession);
CREATE INDEX idx_dra_experiment_02 ON t_dra_experiment (submission);
CREATE INDEX idx_dra_experiment_03 ON t_dra_experiment (status);
CREATE INDEX idx_dra_experiment_04 ON t_dra_experiment (updated);
CREATE INDEX idx_dra_experiment_05 ON t_dra_experiment (visibility);
CREATE INDEX idx_dra_experiment_06 ON t_dra_experiment (experiment);
CREATE INDEX idx_dra_experiment_07 ON t_dra_experiment (sample);
CREATE INDEX idx_dra_experiment_08 ON t_dra_experiment (study);
CREATE INDEX idx_dra_experiment_09 ON t_dra_experiment (biosample);
CREATE INDEX idx_dra_experiment_10 ON t_dra_experiment (bioproject);
CREATE INDEX idx_dra_analysis_01 ON t_dra_analysis (accession);
CREATE INDEX idx_dra_analysis_02 ON t_dra_analysis (submission);
CREATE INDEX idx_dra_analysis_03 ON t_dra_analysis (status);
CREATE INDEX idx_dra_analysis_04 ON t_dra_analysis (updated);
CREATE INDEX idx_dra_analysis_05 ON t_dra_analysis (visibility);
CREATE INDEX idx_dra_analysis_06 ON t_dra_analysis (experiment);
CREATE INDEX idx_dra_analysis_07 ON t_dra_analysis (sample);
CREATE INDEX idx_dra_analysis_08 ON t_dra_analysis (study);
CREATE INDEX idx_dra_analysis_09 ON t_dra_analysis (biosample);
CREATE INDEX idx_dra_analysis_10 ON t_dra_analysis (bioproject);
CREATE INDEX idx_dra_run_01 ON t_dra_run (accession);
CREATE INDEX idx_dra_run_02 ON t_dra_run (submission);
CREATE INDEX idx_dra_run_03 ON t_dra_run (status);
CREATE INDEX idx_dra_run_04 ON t_dra_run (updated);
CREATE INDEX idx_dra_run_05 ON t_dra_run (visibility);
CREATE INDEX idx_dra_run_06 ON t_dra_run (experiment);
CREATE INDEX idx_dra_run_07 ON t_dra_run (sample);
CREATE INDEX idx_dra_run_08 ON t_dra_run (study);
CREATE INDEX idx_dra_run_09 ON t_dra_run (biosample);
CREATE INDEX idx_dra_run_10 ON t_dra_run (bioproject);
CREATE INDEX idx_dra_study_01 ON t_dra_study (accession);
CREATE INDEX idx_dra_study_02 ON t_dra_study (submission);
CREATE INDEX idx_dra_study_03 ON t_dra_study (status);
CREATE INDEX idx_dra_study_04 ON t_dra_study (updated);
CREATE INDEX idx_dra_study_05 ON t_dra_study (visibility);
CREATE INDEX idx_dra_study_06 ON t_dra_study (experiment);
CREATE INDEX idx_dra_study_07 ON t_dra_study (sample);
CREATE INDEX idx_dra_study_08 ON t_dra_study (study);
CREATE INDEX idx_dra_study_09 ON t_dra_study (biosample);
CREATE INDEX idx_dra_study_10 ON t_dra_study (bioproject);
CREATE INDEX idx_dra_sample_01 ON t_dra_sample (accession);
CREATE INDEX idx_dra_sample_02 ON t_dra_sample (submission);
CREATE INDEX idx_dra_sample_03 ON t_dra_sample (status);
CREATE INDEX idx_dra_sample_04 ON t_dra_sample (updated);
CREATE INDEX idx_dra_sample_05 ON t_dra_sample (visibility);
CREATE INDEX idx_dra_sample_06 ON t_dra_sample (experiment);
CREATE INDEX idx_dra_sample_07 ON t_dra_sample (sample);
CREATE INDEX idx_dra_sample_08 ON t_dra_sample (study);
CREATE INDEX idx_dra_sample_09 ON t_dra_sample (biosample);
CREATE INDEX idx_dra_sample_10 ON t_dra_sample (bioproject);
