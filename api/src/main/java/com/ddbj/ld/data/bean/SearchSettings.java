package com.ddbj.ld.data.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SearchSettings {
	@Data
	public static class Options {
		// 画面のプルダウン用
		private String value;
		private String label;
	}

	private List<Options> samples;
	private List<Options> instruments;
}
