package com.ddbj.ld.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:ddbj-batch.properties", encoding = "UTF-8")
public class FileConfig {
	public static class Path {
		public final String bioProject;
		public final String bioSample;
		public final String dra;
		public final String jga;
		public final String sraAccessions;

		private Path(
				String bioProject,
				String bioSample,
				String dra,
				String jga,
				String sraAccessions
		) {
			this.bioProject = bioProject;
			this.bioSample = bioSample;
			this.dra = dra;
			this.jga = jga;
			this.sraAccessions = sraAccessions;
		}
	}

	public final Path path;

	public FileConfig(
			// ファイルパス設定
			@Value( "${file.path.bio-project}" ) String bioProject,
			@Value( "${file.path.bio-sample}" ) String bioSample,
			@Value( "${file.path.dra}" ) String dra,
			@Value( "${file.path.jga}" ) String jga,
			@Value( "${file.path.sra-accessions}" ) String sraAccessions
	) {
		this.path = new Path(
				bioProject,
				bioSample,
				dra,
				jga,
				sraAccessions
		);
	}
}
