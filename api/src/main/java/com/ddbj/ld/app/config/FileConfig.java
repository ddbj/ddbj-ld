package com.ddbj.ld.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:ddbj-api.properties", encoding = "UTF-8")
public class FileConfig {
	public static class FilePath {
		public final String ROOT;
		public final String JVAR;
		public final String BIO_PROJECT;
		public final String BIO_SAMPLE;
		public final String TRAD;
		public final String RESORUCES;

		private FilePath(
				String root,
				String jvar,
				String bioProject,
				String bioSample,
				String trad,
				String resources
		) {
			this.ROOT = root;
			this.JVAR = jvar;
			this.BIO_PROJECT = bioProject;
			this.BIO_SAMPLE = bioSample;
			this.TRAD = trad;
			this.RESORUCES = resources;
		}
	}

	public final FilePath path;

	public FileConfig(
			// ファイルパス設定
			@Value( "${file.dir.root}" ) String file_dir_root,
			@Value( "${file.dir.jvar}" ) String file_dir_jvar,
			@Value( "${file.dir.bioproject}" ) String file_dir_bioproject,
			@Value( "${file.dir.biosample}" ) String file_dir_biosample,
			@Value( "${file.dir.trad}" ) String file_dir_trad,
			@Value( "${file.dir.resources}" ) String file_dir_resource
	) {
		this.path = new FilePath(
				file_dir_root,
				file_dir_root + "/" + file_dir_jvar,
				file_dir_root + "/" + file_dir_bioproject,
				file_dir_root + "/" + file_dir_biosample,
				file_dir_root + "/" + file_dir_trad,
				file_dir_resource
		);
	}
}
