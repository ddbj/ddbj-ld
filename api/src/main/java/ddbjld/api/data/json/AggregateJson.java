package ddbjld.api.data.json;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ddbjld.api.common.utility.JsonMapper;

// t_project.aggregate_json
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AggregateJson {
	
	// プロジェクト詳細画面の「概要」タブや、検索画面の検索結果ペインに表示するサマリ情報を事前に集計しておく。
	// プロジェクト公開前にもプレビュー機能で表示する必要があるので、メタデータの更新を行う都度集計が必要。
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ExperimentSummary {
		
		/** experiment.experiment_id */
		private String experimentId;
		/** sample（サンプル）数 */
		private long samples;
		/** measurement（測定）数 */
		private long measurments;
		
		
		/** プロジェクトファイル数 */
		private ProjectFiles files; // ここはメタデータ更新時ではなく、ファイルの紐付け時に集計する。
		// TODO：要件が変更された。    ↑だったが、そもそもExperiment単位ではなくProjectで通しの集計で良いらしい。SQLでファイル管理テーブルから一発で引けるレベル。
	}
	
	// ここはエンドポイントの {file-type} 及び、画面表示に合わせている。
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Deprecated // TODO：このクラス自体が不要になる、若しくはProjectViewDataの配下に移動になる。
	public static class ProjectFiles {
		
		/** 生データファイル */
		private long raw;
		/** 解析データファイル */
		private long analysis;
		/** プロジェクト添付ファイル */
		private long project;
	}
	
	/** experiment 単位での集計データ */
	private final List<ExperimentSummary> experiments;
	
	
	// TODO：今後、統計機能の拡張に応じてこの辺にデータが増えてくと思われる。
	
	
	public AggregateJson() {
		this( null );
	}
	public AggregateJson( List<ExperimentSummary> experiments ) {
		this.experiments = null == experiments ? new ArrayList<>() : experiments;
	}
	
	
	public String stringify() {
		return JsonMapper.stringify( this );
	}
	
	public static AggregateJson parse( final String json ) {
		return null == json ? null : JsonMapper.parse( json, AggregateJson.class );
	}
}
