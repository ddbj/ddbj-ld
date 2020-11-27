package ddbjld.api.app.core.module;

import ddbjld.api.app.config.ConfigSet;
<<<<<<< HEAD
import ddbjld.api.common.annotation.Module;
=======
>>>>>>> 差分修正
import ddbjld.api.common.constants.ApiMethod;
import ddbjld.api.common.exceptions.RestApiException;
import ddbjld.api.common.utility.JsonMapper;
import ddbjld.api.common.utility.StringUtil;
import ddbjld.api.common.utility.UrlBuilder;
import ddbjld.api.common.utility.api.RestClient;
import ddbjld.api.common.utility.api.StandardRestClient;
import ddbjld.api.common.utility.data.MetadataAccessor.DataRowReader;
import ddbjld.api.data.json.AggregateJson;
import ddbjld.api.data.json.ExcelSheetData;
import ddbjld.api.data.json.ExcelSheetData.MetadataItems;
import ddbjld.api.data.json.ListdataJson;
import ddbjld.api.data.json.MetadataJson;
import ddbjld.api.data.json.elasticsearch.ESIndexDataContainer;
import ddbjld.api.data.json.elasticsearch.ESProjectIndex;
import ddbjld.api.data.json.elasticsearch.FacetJson;
import ddbjld.api.data.values.ProjectIds;
<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
=======
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
>>>>>>> 差分修正

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

<<<<<<< HEAD
@Module
@AllArgsConstructor
@Slf4j
public class SearchModule {

=======
@Component
@Slf4j
public class SearchModule {

	@Autowired
>>>>>>> 差分修正
	ConfigSet config;
	
	public enum ProjectIndexType {
		
		/** 公開インデックス */
		PUBLIC("public-project"),
		
		/** 非公開インデックス */
		PRIVATE("private-project"),
		;
		
		private final String index;
		
		private ProjectIndexType( String index ) {
			this.index = index;
		}
	}
	
	public String information() {
		
		var client = new StandardRestClient();
		
		final String url = config.elasticsearch.baseUrl;
		final String data = "";
		
		var api = client.get( url, data );
		log.debug( api.response.text );
		log.debug( api.response.body );

		if ( api.response.not2xxSuccessful() ) {
			log.error( "ElasticSearch API Error {} {}"
					, api.response.status
					, api.response.text );
			throw new RestApiException( HttpStatus.INTERNAL_SERVER_ERROR );
		}
		return api.response.body;
	}

	public String putProject(
			final ProjectIds ids, 
			final MetadataExtract.ExtractUnits extract,
			final AggregateJson aggregate,
			final ListdataJson listdata,
			final LocalDateTime firstPublishedAt,
			final ProjectIndexType type ) {

		String[] keywords = extract.keywords();
		String[] species = extract.species();
		String[] devices = extract.devices();
		FacetJson facet = new FacetJson( species, devices );
		log.debug( "project[{}] es-index.検索キーワード {}words.", ids.id, keywords.length );
		log.debug( "project[{}] es-index.生物種名 {}.", ids.id, facet.species.length );
		log.debug( "project[{}] es-index.分析機器 {}.", ids.id, facet.devices.length );
		
		// ■リリース日のコピー。
		// 一覧にも出すので公開インデックスのデータにリリース日を設定する。
		// 初回公開日時を年月日にして公開日（リリース日）とする。
		// ※ ES公開処理時点で first_published_at がnull（未設定）という事は有り得ない
		String publishedDate = DateTimeFormatter.ISO_DATE.format( firstPublishedAt );
		
		// ■index への登録データ型を生成。
		ESProjectIndex index = new ESProjectIndex( 
				ids, 
				keywords, 
				aggregate, 
				listdata,
				facet,
				publishedDate );
		final String data = JsonMapper.stringify( index );
		
		
		// ■PUTするURLを構築

		final String uuid = ids.uuid.toString(); // ProjectUUIDをindexのIDに使用する。
		final String url = UrlBuilder.url( config.elasticsearch.baseUrl ).path( type.index, "_doc", uuid ).build();

		var client = new StandardRestClient();
		
		var api = client.put( url, data );
		log.debug( api.response.text );
		log.debug( api.response.body );
		
		
		if ( api.response.not2xxSuccessful() ) {
			log.error( "ElasticSearch API Error {} {}"
					, api.response.status
					, api.response.text );
			throw new RestApiException( HttpStatus.INTERNAL_SERVER_ERROR );
		}
		return api.response.body;
	}
	
	
	public ESProjectIndex getProject( final ProjectIds ids, final ProjectIndexType type ) {
		
		// GETするURLを構築
		final String uuid = ids.uuid.toString(); // ProjectUUIDをindexのIDに使用する。
		final String url = UrlBuilder.url( config.elasticsearch.baseUrl ).path( type.index, "_doc", uuid ).build();
		
		var client = new StandardRestClient();
		
		var api = client.get( url );
		log.debug( api.response.text );
		log.debug( api.response.body );
		
		if ( api.response.not2xxSuccessful() ) {
			log.error( "ElasticSearch API Error {} {}"
					, api.response.status
					, api.response.text );
			throw new RestApiException( HttpStatus.INTERNAL_SERVER_ERROR );
		}
		String json = api.response.body;
		
		ESIndexDataContainer<ESProjectIndex> es = ESIndexDataContainer.parseProject( json );
		log.debug( "elasticsearch [ _index:{}, _type:{}, _seq_no:{}, _id:{}, _version:{} ] {}."
				, es.index
				, es.type
				, es.seq_no
				, es.id
				, es.version
				, es.found ? "found" : "not found" );
		
		return es.source;
	}
	
	

	public static class MetadataExtract {
		
		/** ElasticSearchキーワード抽出対象外になるシート定義 */
		// FIXME：これは最低でもプロパティ化、理想はDBマスタ化＋ApplicationScopeキャッシュにするのが望ましい。（今は時間が無いのでベタ書き）
		private static final List<String> ES_KEYWORD_IGNORE_SHEETS = Arrays.asList( new String[]{
				"unit_value", // 単位つき値
				"unit", // 単位
				"term", // 用語 
				"type", // 型定義
				"column", // カラム
				"file_format", // ファイル形式
		} );
		
		private static final List<String> ES_FACET_SPECIES_SHEETS = Arrays.asList( new String[] {
				// FIXME：暫定仕様
				// 今後変更の可能性があるらしいので template.json 変更に応じてメンテすること。
				// 【現状】species_name項目を持っているsample_xxxx系シート
				// 【将来】taxonomy_ncbiなどに変更する想定（櫻井先生メールより）
				// プロパティにするかマスタにするかどうするかは変更された時に検討すること。
				// 今は取り敢えずベタ書きで置いておく。
				"sample_animal",
				"sample_plant",
				"sample_other",
				"sample_bacteria",
				"sample_food",
		} );
		private static final List<String> ES_FACET_DEVICES_SHEETS = Arrays.asList( new String[] {
				"measurement_method",
		} );
		
		public static class ExtractUnits {
			private HashSet<String> kw = new HashSet<>();
			private HashSet<String> sn = new HashSet<>();
			private HashSet<String> ad = new HashSet<>();
			
			public String[] keywords() {
				return this.kw.toArray( String[]::new );
			}
			
			public String[] species() {
				return this.sn.toArray( String[]::new );
			}
			
			public String[] devices() {
				return this.ad.toArray( String[]::new );
			}
		}
		
		private static final String[] BLANK = new String[] {};
		
		public static ExtractUnits execute( final MetadataJson metadata ) {
			ExtractUnits distinct = new ExtractUnits();
			
			var dr = new DataRowReader();
			
			// 全シート列挙
			for ( ExcelSheetData sheet : metadata.sheets ) {
				
				// 各種抽出フラグ
				final boolean kw = !ES_KEYWORD_IGNORE_SHEETS.contains( sheet.sheet_id );
				final boolean sn = ES_FACET_SPECIES_SHEETS.contains( sheet.sheet_id );
				final boolean ad = ES_FACET_DEVICES_SHEETS.contains( sheet.sheet_id );
				
				// 何れの抽出対象でもない場合は無駄なのでスキップ。
				if ( !kw && !sn && !ad ) continue;
				
				var rows = sheet.data[0].values();
				for ( MetadataItems row : rows ) {
					dr.bind( row );
					
					// ■キーワード抽出
					// 各カラムのデータを全てHashSetに入れる
					if ( kw ) {
						for ( String[] values : row.values() ) {
							put( distinct.kw, values );
						}
					}
					
					// ■生物種名 抽出
					// 種名をHashSetに入れる
					if ( sn ) {
						// 不要なタグを除去する
						String[] values = StringUtil.removeAll(dr.strings( "species_name", BLANK ), "<i>", "</i>");
						put( distinct.sn, values );
					}
					
					// ■分析機器 抽出
					// 分析機器.分類 をHashSetに入れる
					if ( ad ) {
						String[] values = StringUtil.removeAll(dr.strings( "category", BLANK ), "<i>", "</i>");
						put( distinct.ad, values );
					}
				}
			}
			return distinct;
		}
		
		private static void put( HashSet<String> distinct, String[] values ) {
			for ( String value : values ) {
				if ( null == value ) continue;
				if ( value.isEmpty() ) continue;
				distinct.add( value );
			}
		}
	}

	public LinkedHashMap<String, Object> get(final String type, final String identifier) {
		var url = UrlBuilder.url(config.elasticsearch.baseUrl, type, "_doc", identifier).build();
		var restClient = new RestClient();

		var result = restClient.exchange(url, ApiMethod.GET, null, null);

		if(result.response.status == 200) {
			// 何もしない
		} else {
			// FIXME メッセージも入れられるようにする
			throw new RestApiException(HttpStatus.valueOf(result.response.status));
		}

		LinkedHashMap<String, Object> map = JsonMapper.parse(result.response.body, Map.class);

		if(map.containsKey("_source")) {
			return (LinkedHashMap<String, Object>)map.get("_source");
		} else {
			throw new RestApiException(HttpStatus.NOT_FOUND);
		}
	}

	public LinkedHashMap<String, Object> getJsonLd(final String type, final String identifier) {
		// Elasticsearchからデータを取得
		LinkedHashMap<String, Object> graph = this.get(type, identifier);

		// JsonLDを作成
		LinkedHashMap<String, Object> jsonLd = new LinkedHashMap<>();
		jsonLd.put("@graph", graph);
<<<<<<< HEAD
		jsonLd.put("@context", config.jsonLd.url);
=======
		// FIXME URLは外部定義化
		jsonLd.put("@context", config.jsonLdConfig.url);
>>>>>>> 差分修正

		return jsonLd;
	}
}
