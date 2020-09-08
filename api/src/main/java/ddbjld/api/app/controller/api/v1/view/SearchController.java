package ddbjld.api.app.controller.api.v1.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ddbjld.api.app.config.ConfigSet;
import ddbjld.api.app.service.dao.FacetItemsDao;
import ddbjld.api.common.constants.SystemCode;
import ddbjld.api.common.exceptions.RestApiException;
import ddbjld.api.common.utility.JsonMapper;
import ddbjld.api.common.utility.JsonMapper.MapContext;
import ddbjld.api.common.utility.StringUtil;
import ddbjld.api.common.utility.UrlBuilder;
import ddbjld.api.common.utility.api.StandardRestClient;
import ddbjld.api.data.beans.SearchCondition;
import ddbjld.api.data.beans.SearchSettings;
import ddbjld.api.data.beans.SearchSettings.Options;
import ddbjld.api.data.beans.SearchCondition.OperationType;
import ddbjld.api.data.json.elasticsearch.ESQuery;


@RestController
@RequestMapping(path = {
		"v1/view/search", 
		"view/search"
})
public class SearchController {
	
	private static final Logger log = LoggerFactory.getLogger( SearchController.class );
	
	@Autowired
	ConfigSet config;
	
	@Autowired
	FacetItemsDao facetItemsDao;
	
	
	/** 検索画面設定値取得 */
	@GetMapping("/setting")
	// 【重要】現状、公開プロジェクトのみ検索、ログイン不要機能なのでAuthも何も使わない。
	public SearchSettings getSetting( final HttpServletResponse response ) {
		
		List<Options> species = new ArrayList<>();
		List<Options> devices = new ArrayList<>();
		
		// t_facet_items テーブルのデータを全件取得してそれぞれのリストで返す。
		// TODO：t_facet_items の更新頻度が読めないので都度変更・都度取得にしているが、バッチ更新やApplicationScopeレベルのキャッシュを検討したほうが良い。
		var reader = new FacetItemsDao.TFacetItemsDataReader();
		var datasource = this.facetItemsDao.all();
		log.debug( "★ファセット検索データ {}", datasource.size() );
		for ( Map<String, Object> data : datasource ) {
			
			reader.bind( data );
			final String category = reader.category();
			final String item = reader.item();

			// 画面のプルダウン用
			var option = new Options();
			option.setValue( item );
			option.setLabel( item );

			switch ( category ) {
				
				case SystemCode.FACET_ITEM_CATEGORY.SPECIES:

					species.add( option );
					break;
				
				case SystemCode.FACET_ITEM_CATEGORY.DEVICES:
					devices.add( option );
					break;
				
				default:
					continue;
			}
		}
		
		// 画面用のbeanに詰めて返す。
		SearchSettings settings = new SearchSettings();
		settings.setSamples( species );
		settings.setInstruments( devices );
		return settings;
	}
	
	
	// 一応 GET POST 両対応
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	// 【重要】現状、公開プロジェクトのみ検索、ログイン不要機能なのでAuthも何も使わない。
	public String search (
			final HttpServletResponse response, 
			final @RequestBody String body
	) {
		log.debug( "★★検索リクエスト：{}", body );
		
		// ■検索クエリを構築。
		// 検索条件を受け取り。
		SearchCondition condition = SearchCondition.parse( body );
		
		// 検索条件を基にクエリを構築。
		final String query = this.buildQueryString( condition );
		
		
		// ■ElasticSearch Searchエンドポイントに検索クエリを投げる。
		// クエリをPOSTするURLを構築。
		final String BASE_URL = this.config.elasticsearch.baseUrl;
		final String url = UrlBuilder.url( BASE_URL ).path( "public-project", "_search" ).build();
		var client = new StandardRestClient();
		
		
		// FIXME：この辺はあとでちゃんとElasticSearchModuleに持っていく。
		var api = client.post( url, query );
		log.debug( "code:{}", api.response.status );
		log.debug( "text:{}", api.response.text );
		if ( api.response.not2xxSuccessful() ) {
			log.error( "ElasticSearch API Error {} {}"
					, api.response.status
					, api.response.text );
			throw new RestApiException( HttpStatus.BAD_REQUEST );
		}
		return api.response.body;
	}

	// FIXME：この辺はあとでちゃんとElasticSearchModuleに持っていく。
	private String buildQueryString( SearchCondition condition ) {

		final var params = new QueryParams( condition );
		final var flags = new QueryConditionFlags( params );

		// ■検索クエリ条件を構築。
		// クエリビルダーを生成。
		var builder = JsonMapper.map()
			// ★ ESからの抽出項目を指定（固定条件）
			.put( "_source", new String[] { "ids", "aggregate", "listdata", "published_date" } );
		
		// ★ ページング指定
		// ページサイズが設定されている場合は from/size を指定。
		if ( 0 < condition.getCount() ) {
			builder
					.put( "from", condition.getOffset() ) // page-index
					.put( "size", condition.getCount() )  // page-size
			;
		}
		
		
		// ★ クエリ本体の構築
		// ■■MultipartQuery
		if ( flags.isMultipartQuery ) {
			// bool-query で複合条件を構築するパターン。
			
			// クエリどうしを どっち（AND|OR）で結合するか;
			final OperationType operation = OperationType.of( condition.getOperation() );
			if ( null == operation ) throw new RestApiException( HttpStatus.BAD_REQUEST );
			
			this.buildMultipartQuery( builder, params, flags, operation );
		}
		// ■■SinglepartQuery
		else if (false == flags.empty){
			// 単一条件で済むパターン。
			this.buildSinglepartQuery( builder, params, flags );
		}
		
		// ■ハイライトオプション
		// 取り敢えずメタデータのキーワード部分を指定しておく。
		builder.nest( "highlight" )
				.nest( "fields" )
				.nest( "metadata" );
		
		
		// ■JSON文字列化して返す。
		final String query = builder.stringify();
		log.debug( "★★検索クエリ：{}", query );
		return query;
	}

	// bool-query を使ったマルチパートクエリの構築。
	private void buildMultipartQuery(
			MapContext builder,
			final QueryParams params,
			final QueryConditionFlags flags,
			final OperationType operation ) {
		
		List<ESQuery.IElasticSearchQuery> queries = new ArrayList<>();
		
		// ■■■■■■■■■■
		// ■複合クエリの構築■
		// ■■■■■■■■■■
		
		// ★個別検索：生物種名
		if ( flags.species ) {
			var query = whichTermTypeQuery( "facet.species.keyword", params.species );
			queries.add( query );
		}
		// ★個別検索：生物種名
		if ( flags.devices ) {
			var query = whichTermTypeQuery( "facet.devices.keyword", params.devices );
			queries.add( query );
		}
		// ★キーワード検索：複数ワード
		if ( flags.keyword ) {
			
			for ( String keyword : params.keywords ) {
				var match = new ESQuery.MatchQuery( "metadata", keyword );
				queries.add( match );
			}
		}
		
		
		
		// ソースコードのパス上有り得るが、実際には有り得ない組み合わせなので雑な例外にしておく。
		if ( 0 == queries.size() ) throw new RuntimeException();
		
		// "query" : { "bool" : { "should|must" : [queries] } }
		builder.nest( "query" )
				.nest( "bool" )
				.put( operation.boolQueryType, queries );
	}

	// 単一条件を用いたシングルパートクエリの構築
	private void buildSinglepartQuery( 
			MapContext builder, 
			final QueryParams params, 
			final QueryConditionFlags flags ) {
		
		ESQuery.IElasticSearchQuery query = null;
		
		// ■■■■■■■■■■
		// ■単一クエリの生成■
		// ■■■■■■■■■■
		// ※ if連打じゃなくてif/elseで行けるけど、大したコストじゃないので見易さを優先。
		
		// ★個別検索：生物種名
		if ( flags.species ) {
			query = whichTermTypeQuery( "facet.species.keyword", params.species );
		}
		
		// ★個別検索：生物種名
		if ( flags.devices ) {
			query = whichTermTypeQuery( "facet.devices.keyword", params.devices );
		}
		
		// ★キーワード検索：単一ワード
		if ( flags.keyword ) {
			query = new ESQuery.MatchQuery( "metadata", params.keywords[0] );
		}
		
		
		
		// ソースコードのパス上有り得るが、実際には有り得ない組み合わせなので雑な例外にしておく。
		if ( null == query ) throw new RuntimeException();
		
		// "query" : { (term|terms|wildcard) }
		builder.put( "query", query );
	}
	
	private static ESQuery.IElasticSearchQuery whichTermTypeQuery( final String property, final String[] values ) {
		return 1 == values.length
				? new ESQuery.TermQuery( property, values[0] )
				: new ESQuery.TermsQuery( property, values ); 
	}


	private static class QueryParams {

		public final String[] keywords;
		public final String[] species;
		public final String[] devices;
		
		public QueryParams( SearchCondition condition ) {
			this.keywords = StringUtil.nvl( condition.getKeyword() );         // キーワード検索（フリーワード）
			this.species  = StringUtil.nvl( condition.getSamples() );     // 生物種名
			this.devices  = StringUtil.nvl( condition.getInstruments() ); // 分析機器の種別
		}
	}
	private static class QueryConditionFlags {
		public final boolean keyword;
		public final boolean species;
		public final boolean devices;
		
		public final boolean empty;
		
		public final boolean isMultipartQuery;
		
		public QueryConditionFlags( final QueryParams params ) {
			// 各検索項目の有無を判定。
			this.keyword = 0 < params.keywords.length;
			this.species = 0 < params.species.length;
			this.devices = 0 < params.devices.length;
			
			// キーワード検索がマルチワードか否か。
			final boolean multiword = 1 < params.keywords.length;
			
			// 何れかのファセット検索（個別検索）の条件があるか否か。
			final boolean facet = this.species || this.devices;
			
			// 何れの検索条件もない
			this.empty = !this.keyword && !facet;
			
			// ■bool-query でのマルチクエリ化が必要になるパターン判定■
			// ★ 「キーワード検索：複数ワード」条件がある
			if ( multiword ) {
				// マルチクエリで確定。
				this.isMultipartQuery = true;
			}
			// ★ 「キーワード検索：単一ワード」条件がある
			else if ( this.keyword ) {
				// 個別検索がいずれか使用されている場合 true
				this.isMultipartQuery = facet;
			}
			// ★ 「キーワード検索」が使用されない場合。
			else {
				// 個別検索が複数使用されている場合 true 
				this.isMultipartQuery = this.species && this.devices;
			}
		}
	}
}
