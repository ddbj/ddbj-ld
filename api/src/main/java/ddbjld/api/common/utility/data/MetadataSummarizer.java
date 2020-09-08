package ddbjld.api.common.utility.data;

import java.util.*;

import org.springframework.http.HttpStatus;

import lombok.extern.slf4j.Slf4j;
import ddbjld.api.common.exceptions.RestApiException;
import ddbjld.api.common.utility.data.MetadataAccessor.DataRowReader;
import ddbjld.api.data.json.AggregateJson;
import ddbjld.api.data.json.AggregateJson.ExperimentSummary;
import ddbjld.api.data.json.AggregateJson.ProjectFiles;
import ddbjld.api.data.json.ExcelSheetData;
import ddbjld.api.data.json.ListdataJson;
import ddbjld.api.data.json.ExcelSheetData.MetadataItems;
import ddbjld.api.data.json.ListdataJson.PersonData;
import ddbjld.api.data.json.ListdataJson.ProjectPersons;

// プロジェクトのメタデータに対して何かしらの集計処理を行うユーティリティ。
// 現状想定しているのは以下のような処理で、複数Serviceから利用する。
// - メタデータ更新時のaggregate_json生成
// - プロジェクト公開時のpulldown_items集計（検索画面の検索条件設定データを生成する）
@Slf4j
public class MetadataSummarizer {

	/**
	 * シート名（sheet_id）
	 * @author sugaryo
	 */
	private static final class Sheet {
		public static final String EXPERIMENT = "experiment";
		public static final String MEASUREMENT = "measurement";
		public static final String PROJECT = "project"; 
		public static final String PERSON = "person";
	}
	/**
	 * measurementシートのカラム名（label_id）
	 * @author sugaryo
	 */
	private static final class Measurement {
		public static final String MEASUREMENT_ID = "measurement_id";
		public static final String EXPERIMENT_ID = "experiment_id";
		public static final String SAMPLE_ID = "sample_id"; 
	}
	/**
	 * projectシートのカラム名（label_id）
	 * @author sugaryo
	 */
	private static final class Project {
		public static final String CREATOR = "person_creator";
		public static final String CONTACT = "person_contact";
		public static final String PRINCIPAL_INVESTIGATOR = "person_principal_investigator";
		public static final String SUBMITTER = "person_submitter";
	}
	
	
	private final String projectId;
	/** 集計基礎データ（ExcelToolで入力するメタデータ部：t_project.metadata_json） */
	private final ExcelSheetData[] datasource;
	/** メタデータに対するアクセスユーティリティ */
	private final MetadataAccessor accessor;

	public MetadataSummarizer( String projectId, ExcelSheetData[] datasource ) {
		this.projectId = projectId;
		this.datasource = datasource;
		this.accessor = MetadataAccessor.compile( this.datasource );
	}
	
	private static class UniqueIdCounter {
		private final HashSet<String> measurements = new HashSet<>();
		private final HashSet<String> samples = new HashSet<>();
	}
	
	
	public AggregateJson sumAggregateJson() {
		
		var reader = new DataRowReader();
		
		
		// ■前処理■
		// ユニークID毎に集計を掛ける為の Map-Set を構築。
		// TODO：コレ自体を部品化したかったが時間が無いので突貫実装、余力があれば更新フロー構築時に対応する。
		LinkedHashMap<String, UniqueIdCounter> groupby = new LinkedHashMap<>();
		
		// Sheet[experiment] を基準に集計データを用意する。（ゼロ件も正しく拾うため）
		var xsExperiment = accessor.sheet( Sheet.EXPERIMENT );

		if(this.projectId.equals("RPMM0012")) {
			System.out.print("RPMM0012");
		}

		if ( xsExperiment.none() ) {
			// [experiment] のデータがない場合
			log.warn( "!project[{}].sheet[{}] is nodata.", this.projectId, Sheet.EXPERIMENT );
		}
		for ( int i = 0; i < xsExperiment.size(); i++ ) {
			String id = xsExperiment.rowId( i );
			groupby.put( id, new UniqueIdCounter() );
		}
		
		
		// ■集計処理■
		// Sheet[measurement] のデータを列挙して集計する。
		var xsMeasurement = accessor.sheet( Sheet.MEASUREMENT );
		if ( xsMeasurement.none() ) {
			// [measurement] のデータがない場合
			log.warn( "!project[{}].sheet[{}] is nodata.", this.projectId, Sheet.MEASUREMENT );
		}
		for ( MetadataItems row : xsMeasurement.rows() ) {
			reader.bind( row );

			
			// ■ID, MeasurementID を取得。
			final String id = reader.string( "id", "" );
			log.debug( "★ measurement[{}]", id );
			
			
			// TODO：この measurement↓ の中身が [measurement].[measurement_id] になってるので、不要になる筈。[measurement].[id] の方をカウントすれば要求を満たせる。
			final String measurement = reader.string( Measurement.MEASUREMENT_ID );
			if ( null == measurement ) {
				log.warn( "!project[{}].sheet[{}].Row[{}] is lost {}."
						, this.projectId
						, Sheet.MEASUREMENT
						, id
						, Measurement.MEASUREMENT_ID );
				continue;
			}
			
			// ■測定の紐づく ExperimentID を取得
			final String experiment = reader.string( Measurement.EXPERIMENT_ID );
			if ( null == experiment ) {
				// experiment_id が設定されていない測定データは無視する。
				log.warn( "!project[{}].sheet[{}].Row[{}] is lost {}."
						, this.projectId
						, Sheet.MEASUREMENT
						, id
						, Measurement.EXPERIMENT_ID );
				continue;
			}
			
			// ■Experiment毎にそれぞれのユニークIDをカウントする。
			var counter = groupby.get( experiment );
			if ( null == counter ) {
				// 存在しないExperimentに紐付いている測定データ。
				// ここでID不一致が発生する場合はユーザデータがおかしいが、取り敢えず集計対象外にする。
				// ※ ここでエラーにしても良いが、このレベルの整合性は申請時のバリデーションでやる。
				log.warn( "!project[{}].sheet[{}].Row[{}] is BAD DATA；存在しないexperiment[{}]に紐づくmeasurement[{}]."
						, this.projectId
						, Sheet.MEASUREMENT
						, id
						, experiment
						, measurement );
				continue;
			}
			// 測定をカウント。
			counter.measurements.add( measurement ); //TODO：ここ← で [measurement].[measurement_id] をユニークカウントする事にしているが、[measurement].[id] をカウントする形に変更すれば良い。
			
			// ■SampleID を取得
			final String sample = reader.string( Measurement.SAMPLE_ID );
			if ( null == sample ) {
				log.warn( "!project[{}].sheet[{}].Row[{}] is lost {}."
						, this.projectId
						, Sheet.MEASUREMENT
						, id
						, Measurement.SAMPLE_ID );
				continue;
			}
			// サンプルをカウント
			counter.samples.add( sample );
		}
		
		
		// ■返却データ■
		// AggregateJson を生成して返す。
		List<ExperimentSummary> summaries = new ArrayList<>(); 
		for ( var entry : groupby.entrySet() ) {
			String experimentId = entry.getKey();
			UniqueIdCounter counter  = entry.getValue();

			ExperimentSummary summary = new ExperimentSummary( 
					experimentId, 
					counter.samples.size(), 
					counter.measurements.size(), 
					new ProjectFiles() );
			summaries.add( summary );
		}
		return new AggregateJson( summaries );
	}
	
	
	public ListdataJson extractListdataJson() {

		Map<String, String[]> titles = new HashMap<>();
		ProjectPersons persons = new ProjectPersons();
		
		
		// ■メタデータから [project], [person] のシートデータを抜いてくる。
		var xsProject = accessor.sheet( Sheet.PROJECT );
		var xsPerson = accessor.sheet( Sheet.PERSON );
		
		if ( 1 != xsProject.size() ) {
			// プロジェクトデータは１件じゃないと流石にNG
			throw new RestApiException( HttpStatus.BAD_REQUEST );
		}
		
		// DataRowReader インスタンスを用意。
		var project = xsProject.row( 0 );
		var drProject = new DataRowReader().bind( project );
		var drPerson = new DataRowReader();
		
		// ■ [project] シートからタイトル系・人物系のデータを抜いてくる。
		for( String projectLabelId : project.keySet() ) {
			
			// ■タイトル系のデータなら titles に抜いてくる。
			if ( projectLabelId.startsWith( "title" ) ) {
				final String[] title = drProject.strings( projectLabelId );
				titles.put( projectLabelId, title );
			}
			// ■人物系のデータならそれぞれ person シートの情報を抜いてくる。
			// ■タイトル系でも関係者でもないケース（default case）なら無視。
			else {
				PersonData data; 
				switch ( projectLabelId ) {
					
					case Project.CREATOR:
						data = persons.getCreator();
						break;
						
					case Project.CONTACT:
						data = persons.getContact();
						break;
						
					case Project.PRINCIPAL_INVESTIGATOR:
						data = persons.getPrincipalInvestigator();
						break;
					
					case Project.SUBMITTER:
						data = persons.getSubmitter();
						break;
						
					default:
						// 関係者項目じゃない場合は無関係なので無視。
						continue;
				}

				// リンク項目なので単一値しか来ない。
				final String personId = DataRowReader.string( project, projectLabelId );
				
				
				// ■■取り敢えず person シートのMapデータが持っているものを全て設定する。
				var person = xsPerson.row( personId );
				drPerson.bind( person );
				
				// person.id のリンク切れデータなら無視しておく。
				// ※ ここでエラーにしても良いが、このレベルの整合性は申請時のバリデーションでやる。
				if ( null == person ) continue;
				
				for ( var personLabelId : person.keySet() ) {
					// ID項目なら string として抜き出し。
					if ( "id".equals( personLabelId ) ) {
						String id = drPerson.string( personLabelId );
						data.setId( id );
					}
					// ID項目以外の属性値なら string[] として抜き出し。
					else {
						String[] values = drPerson.strings( personLabelId );
						data.getData().put( personLabelId, values );
					}
				}
			}
		}
		
		// 抽出・設定したデータを返す。
		return new ListdataJson( titles, persons );
	}
	
}
