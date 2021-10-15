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
			public final String fullPath;
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
					final String dataDir
			) {
				// 取り込み対象が増減した場合はここを変更
				this.basePath = dataDir + "/public/jga";
				this.fullPath = this.basePath + "/full/xml";
				this.analysisStudy = this.fullPath + "/analysis-study-relation.csv";
				this.dataExperiment = this.fullPath + "/data-experiment-relation.csv";
				this.dataSetAnalysis = this.fullPath + "/dataset-analysis-relation.csv";
				this.dataSetData = this.fullPath + "/dataset-data-relation.csv";
				this.dataSetPolicy = this.fullPath + "/dataset-policy-relation.csv";
				this.experimentStudy = this.fullPath + "/experiment-study-relation.csv";
				this.policyDac = this.fullPath + "/policy-dac-relation.csv";
				this.date = this.fullPath + "/date.csv";
				this.study = this.fullPath + "/jga-study.xml";
				this.dataSet = this.fullPath + "/jga-dataset.xml";
				this.policy = this.fullPath + "/jga-policy.xml";
				this.dac = this.fullPath + "/jga-dac.xml";
			}
		}

		public static class BioProject {
			public final String basePath;
			public final String fullPath;
			public final String ncbi;
			public final String ddbj;

			private BioProject(
					final String dataDir
			) {
				this.basePath = dataDir + "/public/bioproject";
				this.fullPath = this.basePath + "/full/xml";
				this.ncbi = this.fullPath + "/bioproject.xml";
				this.ddbj = this.fullPath + "/ddbj_core_bioproject.xml";
			}
		}

		public static class BioSample {
			public final String basePath;
			public final String fullPath;
			public final String ncbi;
			public final String ddbj;

			private BioSample(
					final String dataDir
			) {
				this.basePath = dataDir + "/public/biosample";
				this.fullPath = this.basePath + "/full/xml";
				this.ncbi = basePath + "/biosample_set.xml";
				this.ddbj = basePath + "/ddbj_biosample_set.xml";
			}
		}

		public static class Sra {
			public final String basePath;
			public final String fullPath;
			public final String accessions;
			public final String ncbi;
			public final String ebi;
			public final String ddbj;

			private Sra(
					final String dataDir
			) {
				this.basePath = dataDir + "/public/sra";
				this.fullPath = this.basePath + "/full/xml";
				this.accessions = this.basePath + "/full/accessions";
				this.ncbi = basePath + "/SRA";
				this.ebi = basePath + "/ERA";
				this.ddbj = basePath + "/DRA";
			}
		}

		public final String outDir;
		public final BioProject bioProject;
		public final BioSample bioSample;
		public final Sra sra;
		public final Jga jga;

		private Path(
				final String outDir,
				final String dataDir
		) {
			this.outDir = outDir;
			this.bioProject = new BioProject(dataDir);
			this.bioSample = new BioSample(dataDir);
			this.sra = new Sra(dataDir);
			this.jga = new Jga(dataDir);
		}
	}

	public static class FTP {
		public String ncbi;

		private FTP(
			final String ncbi
		) {
			this.ncbi = ncbi;
		}
	}

	public final Path path;
	public final FTP ftp;

	public FileConfig(
			// ファイルパス設定
			@Value( "${file.path.out-dir}" ) final String outDir,
			@Value( "${file.path.data-dir}" ) final String dataDir,
			@Value( "${file.ftp.ncbi}" ) final String ncbiFTP
	) {
		this.path = new Path(
				outDir,
				dataDir
		);

		this.ftp = new FTP(
				ncbiFTP
		);
	}
}
