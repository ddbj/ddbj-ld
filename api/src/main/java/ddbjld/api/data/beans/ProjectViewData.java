package ddbjld.api.data.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ddbjld.api.data.json.AggregateJson;
import ddbjld.api.data.json.ListdataJson;
import ddbjld.api.data.json.MetadataJson;
import ddbjld.api.data.values.DraftMetadata;
import ddbjld.api.data.values.ProjectIds;

import java.util.List;

// プロジェクトの共通データクラス。
// 用途ごとにJSONデータクラスを使い分けるのではなく、
// このデータクラス自体は共有して属性を使い分ける。
// （受け取り側のJSONデータ構造を出来るだけ寄せるので）
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ProjectViewData {
	
	// TODO：足りないものがあったら追加する。
	
	
	// ■必須：プロジェクトのIDとUUID
	
	/** プロジェクトのID情報 */
	private final ProjectIds ids;
	
	// □任意：各種JSONデータ部
	
	/** メタデータ（t_project.metadata_json） */
	private MetadataJson metadata;

	/** 公開されているファイルのリスト（t_file） */
	private List<ProjectFileInfo> publicFileList;
	
	/** 集計データ（t_project.aggregate_json） */
	private AggregateJson aggregate;
	
	/** 一覧画面用データ（t_project.listdata_json） */
	private ListdataJson listdata;

	/** 編集中のメタデータ（t_draft_metadata.metadata_json） */
	private DraftMetadata draftMetadata;

	/** 編集中のファイルのリスト（t_file） */
	private List<ProjectFileInfo> draftFileList;
	
	// □任意：プロジェクトの状態情報
	
	/** リリース日（初回公開日時;t_project.first_published_at の年月日） */
	private String publishedDate;
	
	/** プロジェクト編集中か否か（t_project.editing） */
	private boolean editing;
	
	/** プロジェクトが非公開化されているか否か（t_project.hidden_at[!null]） */
	private boolean hidden; // TODO：設定処理がまだ無いが先にJSON形式だけ確定させたい。
	
}
