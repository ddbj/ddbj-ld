package com.ddbj.ld.app.config;

import com.ddbj.ld.common.constants.FileNameEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:ddbj-batch.properties", encoding = "UTF-8")
public class FileConfig {
	// FIXME 各DBごとにクラスを分ける、設定ファイルの.pathの表記を消す
	public static class Path {
		public final String bioProject;
		public final String bioSample;
		public final String dra;
		public final String jga;
		public final String sraAccessions;

		private Path(
				final String bioProject,
				final String bioSample,
				final String dra,
				final String jga,
				final String sraAccessions
		) {
			this.bioProject = bioProject;
			this.bioSample = bioSample;
			this.dra = dra;
			this.jga = jga;
			this.sraAccessions = sraAccessions;
		}
	}

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

	public final Path path;
	public final Jga jga;

	public FileConfig(
			// ファイルパス設定
			@Value( "${file.path.bio-project}" ) final String bioProject,
			@Value( "${file.path.bio-sample}" ) final String bioSample,
			@Value( "${file.path.dra}" ) final String dra,
			@Value( "${file.path.jga}" ) final String jga,
			@Value( "${file.path.sra-accessions}" ) final String sraAccessions
	) {
		this.path = new Path(
				bioProject,
				bioSample,
				dra,
				jga,
				sraAccessions
		);

		this.jga = new Jga(jga);
	}
}
