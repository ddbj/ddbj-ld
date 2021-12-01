package com.ddbj.ld.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:ddbj-batch.properties", encoding = "UTF-8")
public class FileConfig {

	public static class Path {

		public static class JGA {
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

			private JGA (
					final String extDataDir
			) {
				// 取り込み対象が増減した場合はここを変更
				this.basePath = extDataDir + "/jga";
				this.analysisStudy = this.basePath + "/analysis-study-relation.csv";
				this.dataExperiment = this.basePath + "/data-experiment-relation.csv";
				this.dataSetAnalysis = this.basePath + "/dataset-analysis-relation.csv";
				this.dataSetData = this.basePath + "/dataset-data-relation.csv";
				this.dataSetPolicy = this.basePath + "/dataset-policy-relation.csv";
				this.experimentStudy = this.basePath + "/experiment-study-relation.csv";
				this.policyDac = this.basePath + "/policy-dac-relation.csv";
				this.date = this.basePath + "/date.csv";
				this.study = this.basePath + "/jga-study.xml";
				this.dataSet = this.basePath + "/jga-dataset.xml";
				this.policy = this.basePath + "/jga-policy.xml";
				this.dac = this.basePath + "/jga-dac.xml";
			}
		}

		public static class BioProject {
			public final String basePath;
			public final String fullPath;
			public final String execDatePath;
			public final String fullXMLPath;
			public final String ncbi;
			public final String ddbj;

			private BioProject(
					final String dataDir,
					final String extDataDir
			) {
				this.basePath = dataDir + "/public/bioproject";
				this.fullPath = this.basePath + "/full";
				this.execDatePath = this.basePath + "/exec_date.txt";
				this.fullXMLPath = this.fullPath + "/xml";
				this.ncbi = this.fullXMLPath + "/bioproject.xml";
				this.ddbj = extDataDir + "/bioproject/ddbj_core_bioproject.xml";
			}
		}

		public static class BioSample {
			public final String basePath;
			public final String fullPath;
			public final String execDatePath;
			public final String fullXMLPath;
			public final String ncbi;
			public final String ddbj;

			private BioSample(
					final String dataDir,
					final String extDataDir
			) {
				this.basePath = dataDir + "/public/biosample";
				this.fullPath = this.basePath + "/full";
				this.execDatePath = this.basePath + "/exec_date.txt";
				this.fullXMLPath = this.fullPath + "/xml";
				this.ncbi = this.fullXMLPath + "/biosample_set.xml";
				this.ddbj = extDataDir + "/biosample/ddbj_biosample_set.xml.gz";
			}
		}

		public static class SRA {
			public final String basePath;
			public final String fullPath;
			public final String execDatePath;
			public final String accessionLastUpdatedPath;
			public final String fullXMLPath;
			public final String accessionsPath;
			public final String accessions;
			public final String livelist;
			public final String ncbi;
			public final String ebi;
			public final String ddbj;

			private SRA(
					final String dataDir,
					final String extDataDir
			) {
				this.basePath = dataDir + "/public/sra";
				this.fullPath = this.basePath + "/full";
				this.execDatePath = this.basePath + "/exec_date.txt";
				this.accessionLastUpdatedPath = this.basePath + "/accessions_last_updated.txt";
				this.fullXMLPath = this.fullPath + "/xml";
				this.accessionsPath = this.fullPath + "/accessions";
				this.accessions = this.accessionsPath + "/SRA_Accessions.tab";
				this.livelist = extDataDir + "/sra/livelist";
				this.ncbi = this.fullXMLPath + "/SRA";
				this.ebi = this.fullXMLPath + "/ERA";
				this.ddbj = extDataDir + "/sra/DRA";
			}
		}

		public final String outDir;
		public final String dataDir;
		public final String extDataDir;
		public final String publicDir;
		public final BioProject bioProject;
		public final BioSample bioSample;
		public final SRA sra;
		public final JGA jga;

		private Path(
				final String outDir,
				final String dataDir,
				final String extDataDir
		) {
			this.outDir = outDir;
			this.dataDir = dataDir;
			this.extDataDir = extDataDir;
			this.publicDir = dataDir + "/public";
			this.bioProject = new BioProject(dataDir, extDataDir);
			this.bioSample = new BioSample(dataDir, extDataDir);
			this.sra = new SRA(dataDir, extDataDir);
			this.jga = new JGA(extDataDir);
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
			@Value( "${file.path.ext-data-dir}" ) final String extDataDir,
			@Value( "${file.ftp.ncbi}" ) final String ncbiFTP
	) {
		this.path = new Path(
				outDir,
				dataDir,
				extDataDir
		);

		this.ftp = new FTP(
				ncbiFTP
		);
	}
}
