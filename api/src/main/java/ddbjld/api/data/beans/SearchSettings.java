package ddbjld.api.data.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

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
