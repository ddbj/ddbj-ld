package com.ddbj.ld.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:ddbj-batch.properties", encoding = "UTF-8")
public class FileConfig {

	public static class Path {

		public static class Jga {
			public final String basePath;
			public final String analysisStudy;
			public final String dataExperiment;
			public final String dataSetAnalysis;
			public final String dataSetData;
			public final String dataSetPolicy;
			public final String experimentStudy;
			public final String policyDac;
			public final String date;
			public final String study;
			public final String dataSet;
			public final String policy;
			public final String dac;

			private Jga(
					final String basePath
			) {
				// 取り込み対象が増減した場合はここを変更
				this.basePath = basePath;
				this.analysisStudy = basePath + "/analysis-study-relation.csv";
				this.dataExperiment = basePath + "/data-experiment-relation.csv";
				this.dataSetAnalysis = basePath + "/dataset-analysis-relation.csv";
				this.dataSetData = basePath + "/dataset-data-relation.csv";
				this.dataSetPolicy = basePath + "/dataset-policy-relation.csv";
				this.experimentStudy = basePath + "/experiment-study-relation.csv";
				this.policyDac = basePath + "/policy-dac-relation.csv";
				this.date = basePath + "/date.csv";
				this.study = basePath + "/jga-study.xml";
				this.dataSet = basePath + "/jga-dataset.xml";
				this.policy = basePath + "/jga-policy.xml";
				this.dac = basePath + "/jga-dac.xml";
			}
		}

		public static class BioProject {
			public final String basePath;
			public final String ncbi;
			public final String ddbj;

			private BioProject(
					final String basePath
			) {
				this.basePath = basePath;
				this.ncbi = basePath + "/bioproject.xml";
				this.ddbj = basePath + "/ddbj_core_bioproject.xml";
			}
		}

		public static class BioSample {
			public final String basePath;
			public final String ncbi;
			public final String ddbj;
			public final String outDir;

			private BioSample(
					final String basePath,
					final String outDir
			) {
				this.basePath = basePath;
				this.ncbi = basePath + "/biosample_set.xml";
				this.ddbj = basePath + "/ddbj_biosample_set.xml";
				this.outDir = outDir;
			}
		}

		public static class Sra {
			public final String basePath;
			public final String ncbi;
			public final String ebi;
			public final String ddbj;
			public final String accessions;

			private Sra(
					final String basePath,
					final String accessions
			) {
				// FIXME スパコンの配置場所によってはもっと自由にパスを設定出来るようにする
				this.basePath = basePath;
				this.ncbi = basePath + "/SRA";
				this.ebi = basePath + "/ERA";
				this.ddbj = basePath + "/DRA";
				this.accessions = accessions;
			}
		}

		public final BioProject bioProject;
		public final BioSample bioSample;
		public final Sra sra;
		public final Jga jga;

		private Path(
				final String bioProject,
				final String bioSample,
				final String bioSampleOutDir,
				final String sra,
				final String jga,
				final String accessions
		) {
			this.bioProject = new BioProject(bioProject);
			this.bioSample = new BioSample(bioSample, bioSampleOutDir);
			this.sra = new Sra(sra, accessions);
			this.jga = new Jga(jga);
		}
	}

	public final Path path;

	public FileConfig(
			// ファイルパス設定
			@Value( "${file.path.bioproject}" ) final String bioProject,
			@Value( "${file.path.biosample}" ) final String bioSample,
			@Value( "${file.path.biosample.out}" ) final String bioSampleOutDir,
			@Value( "${file.path.sra}" ) final String sra,
			@Value( "${file.path.sra.accessions}" ) final String accessions,
			@Value( "${file.path.jga}" ) final String jga
	) {
		this.path = new Path(
				bioProject,
				bioSample,
				bioSampleOutDir,
				sra,
				jga,
				accessions
		);
	}
}
