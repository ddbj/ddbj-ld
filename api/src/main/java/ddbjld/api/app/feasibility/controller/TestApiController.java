package ddbjld.api.app.feasibility.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ddbjld.api.app.config.ConfigSet;
import ddbjld.api.app.config.TemplateJson;
import ddbjld.api.app.core.module.AuthModule;
import ddbjld.api.app.core.module.ElasticSearchModule;
import ddbjld.api.app.core.module.ElasticSearchModule.MetadataExtract;
import ddbjld.api.app.core.module.ElasticSearchModule.ProjectIndexType;
import ddbjld.api.app.core.module.FileModule;
import ddbjld.api.app.service.ProjectService;
import ddbjld.api.app.service.PublishService;
import ddbjld.api.app.service.dao.AccountDao;
import ddbjld.api.app.service.dao.ProjectDao;
import ddbjld.api.app.service.dao.ProjectDao.TProjectDataReader;
import ddbjld.api.app.service.dao.ProjectRoleDao;
import ddbjld.api.app.service.dao.UserDao;
import ddbjld.api.common.exceptions.RestApiException;
import ddbjld.api.common.utility.JsonMapper;
import ddbjld.api.common.utility.UrlBuilder;
import ddbjld.api.common.utility.api.StandardRestClient;
import ddbjld.api.common.utility.data.MetadataAccessor;
import ddbjld.api.common.utility.data.MetadataSummarizer;
import ddbjld.api.data.beans.ProjectViewData;
import ddbjld.api.data.json.AggregateJson;
import ddbjld.api.data.json.AggregateJson.ExperimentSummary;
import ddbjld.api.data.json.AggregateJson.ProjectFiles;
import ddbjld.api.data.json.elasticsearch.ESProjectIndex;
import ddbjld.api.data.json.elasticsearch.FacetJson;
import ddbjld.api.data.json.ExcelSheetData;
import ddbjld.api.data.json.ListdataJson;
import ddbjld.api.data.json.MetadataJson;
import ddbjld.api.data.values.DraftMetadata;
import ddbjld.api.data.values.ProjectIds;


// 動作確認用。
@Slf4j
@RequestMapping({
	"ddbj/test-api",
	"test-api",
	"sugaryo",
})
@RestController
public class TestApiController {
	
	@Data
	private static class SampleData {
		private String sampleName;
		private int sampleAge;
		private LocalDate sampleDate;
		private LocalDateTime sampleDateTime;
	}

	@RequestMapping("/jackson/bean")
	public SampleData sample_data_bean() {
		var data = new SampleData();
		data.setSampleName( "jackson.SpringRestController" );
		data.setSampleAge( 297 );
		var now = LocalDateTime.now();
		data.setSampleDate( now.toLocalDate() );
		data.setSampleDateTime( now );
		
		return data;
	}
	@RequestMapping("/jackson/text")
	public String sample_data_text() {
		var data = new SampleData();
		data.setSampleName( "jackson.utility.JsonMapper" );
		data.setSampleAge( 12345 );
		var now = LocalDateTime.now();
		data.setSampleDate( now.toLocalDate() );
		data.setSampleDateTime( now );
		
		return JsonMapper.stringify( data );
	}
	

	@Autowired
	ProjectDao daoProject;


	
	@RequestMapping("create-project")
	public ProjectIds testCreateProject(HttpServletRequest request) {
		
		UUID ownerAccountUUID = authorize.getAuthAccountUUID( request, false );

		return this.projectService.createProject( ownerAccountUUID );
		
	}
	
	

	@RequestMapping({
		"project/uuid/{id}", 
		"pjuuid/{id}",
	})
	public ProjectIds testProjectUUID(@PathVariable String id) {
		// ID から UUID を検索
		var ids = this.daoProject.ids( id );
		if ( null == ids ) throw new RestApiException( HttpStatus.NOT_FOUND );
		return ids;
	}
	@RequestMapping({
		"project/id/{uuid}", 
		"pjid/{uuid}",
	})
	public ProjectIds testProjectId(@PathVariable UUID uuid) {
		// UUID から ID を取得
		var ids = this.daoProject.ids( uuid );
		if ( null == ids ) throw new RestApiException( HttpStatus.NOT_FOUND );
		return ids;
	}
	
	
	@Autowired
	UserDao daoUser;
	
	@Autowired
	AccountDao daoAccount;
	
	@PostMapping({
		"user/{uid}",
	})
	public String testCreateUser(@PathVariable String uid) {
		
		UUID accountUUID = this.daoAccount.create( uid, "dummy-refresh-token" );
		
		UUID userUUID = this.daoUser.create( accountUUID );
		
		return JsonMapper.map()
				.put( "account_uuid", accountUUID )
				.put( "user_uuid", userUUID )
				.stringify();
	}
	
	
	
	@Autowired
	ProjectRoleDao daoProjectRole;
	
	// 動作検証用に ProjectId＋account.uid で関係者登録（共同編集者固定）出来るようにしたTestAPI
	// 正式実装の invite 機能では ProjectId＋account.uuid で登録する。
	@RequestMapping( "role/{projectId}/{uid}" ) 
	public String createProjectRole( @PathVariable String projectId, @PathVariable String uid ) {
	
		var account = daoAccount.readByUid( uid );
		var tAccount = new AccountDao.TAccountDataReader( account );
		
		var project = daoProject.read( projectId );
		var tProject = new ProjectDao.TProjectDataReader( project );
		
		
		// 権限データを登録。
		daoProjectRole.createEditor( tProject.uuid(), tAccount.uuid(), null );
		
		return JsonMapper.map()
				.put( "message", "共同編集者（無期限）としてデータ登録" )
				.put( "uid", uid )
				.put( "account_uuid", tAccount.uuid() )
				.put( "project_uuid", tProject.uuid() )
				.put( "project_id", projectId )
				.stringify();
	}
	
	
	@Autowired
	ProjectService projectService;
	
	@GetMapping({
		"project-view/{projectId}",
		"pv/{projectId}",
	})
	public ProjectViewData pv( HttpServletRequest request, @PathVariable String projectId ) {

		UUID accountUUID = authorize.getAuthAccountUUID( request, false );
		
		return this.projectService.getDetailViewData( projectId, accountUUID );
	}
	@GetMapping({
		"pv-null",
	})
	public ProjectViewData pv_null( HttpServletRequest request ) {

		ProjectIds ids = new ProjectIds( UUID.randomUUID(), "TEST00001" );
		ProjectViewData data = new ProjectViewData( ids );
		data.setMetadata( new MetadataJson( new ExcelSheetData[] {} ) );
		data.setAggregate( new AggregateJson() );
		data.setListdata( new ListdataJson() );
		return data;
	}
	
	
	@Autowired
	AuthModule authorize;
	
	@RequestMapping("auth/account") 
	public String auth_account(HttpServletRequest request ) {
		UUID uuid = authorize.getAuthAccountUUID( request, false );
		
		var account = daoAccount.read( uuid );
		String json = JsonMapper.stringify( account );
		return json;
	}
	
	
	// draft metadata のサンプル
	
	@GetMapping("sample/draft")
	public DraftMetadata sample_draft() {
		
		var sheets = load_sampledata();
		String token = "0523df6ccc6dc6df6b831155a1ffd3f3"; // MD5
		DraftMetadata draft = new DraftMetadata( sheets, token );
		
		return draft;
	}
	
	
	private ExcelSheetData[] load_sampledata () {
		String json = load_sampledata_json();
		ExcelSheetData[] sheets = JsonMapper.parse( json, ExcelSheetData[].class );
		return sheets;
	}
	private String load_sampledata_json() {
		
		try {
			// src/main/resources/config/template.json を読み込み
			File file = ResourceUtils.getFile( "classpath:config/sampledata.json" );
			return Files
					.lines( file.toPath(), StandardCharsets.UTF_8 )
					.collect( Collectors.joining( "" ) );
		} catch ( IOException ex ) {
			throw new RuntimeException( ex );
		}
	}
	
	
	

	@GetMapping("sample/aggregate")	
	public AggregateJson sample_aggregate() {
		
		List<ExperimentSummary> experiments = new ArrayList<ExperimentSummary>();
		
		ExperimentSummary[] exps = {
				new ExperimentSummary( "EXP1", 111, 297, new ProjectFiles( 192, 169, 255 ) ),
				new ExperimentSummary( "EXP2", 222, 297, new ProjectFiles( 192, 169, 102 ) ),
				new ExperimentSummary( "EXP3", 333, 297, new ProjectFiles( 192, 169, 103 ) ),
				new ExperimentSummary( "EXP4", 444, 297, new ProjectFiles( 192, 169, 104 ) ),
				new ExperimentSummary( "EXP5", 555, 297, new ProjectFiles( 192, 169, 105 ) ),
		};
		
		experiments.addAll( Arrays.asList( exps ) );
		
		var agg = new AggregateJson( experiments );
		
		// ついでにパーステスト。
		String json = agg.stringify();
		var agg2 = AggregateJson.parse( json );
		
		return agg2;
	}
	
	@GetMapping("test/aggregate")	
	public AggregateJson test_aggregate() {
		
		var sheets = load_sampledata();
		
		MetadataSummarizer sumarizer = new MetadataSummarizer( "DUMMY-DAtA", sheets );
		
		var agg = sumarizer.sumAggregateJson();
		return agg;
	}
	@PostMapping("test/aggregate")	
	public AggregateJson test_aggregate(@RequestBody String body) {
		
		DraftMetadata data = JsonMapper.parse( body, DraftMetadata.class ); 
		
		MetadataSummarizer sumarizer = new MetadataSummarizer( "DUMMY-DAtA", data.metadata );
		
		var agg = sumarizer.sumAggregateJson();
		return agg;
	}
	
	@PostMapping("test/listdata")	
	public ListdataJson test_listdata(@RequestBody String body) {
		
		DraftMetadata data = JsonMapper.parse( body, DraftMetadata.class ); 
		
		MetadataSummarizer sumarizer = new MetadataSummarizer( "DUMMY-DAtA", data.metadata );
		
		var lst = sumarizer.extractListdataJson();
		return lst;
	}

	@PostMapping("test/facet")	
	public FacetJson es_facet(@RequestBody String body) {
		
		DraftMetadata data = JsonMapper.parse( body, DraftMetadata.class ); 
		
		MetadataJson metadata = new MetadataJson( data.metadata );
		var distinct = ElasticSearchModule.MetadataExtract.execute( metadata );
		
		var facet = new FacetJson(
				distinct.species(),
				distinct.devices() );
		return facet;
	}
	
	
	

	@GetMapping("sample/data/{sheet_id}") 
	public ExcelSheetData.MetadataObjects sample_data_sheet( final @PathVariable String sheet_id ) {
		var sheets = load_sampledata();
		
		MetadataAccessor accessor = MetadataAccessor.compile( sheets );
		
		var sheet = accessor.sheet( sheet_id );
		if ( sheet.has() ) {
			return sheet.objects();
		}
		else {
			return null;
		}
	}
	

	@PostMapping("test/edit/{project_id}")
	public ProjectIds test_edit_project(
			HttpServletRequest request,
			@PathVariable String project_id ) {
		
		// 認証ヘッダからアカウント情報を取得。
		UUID authAccountUUID = this.authorize.getAuthAccountUUID( request, true ); // 必須
		
		ProjectIds ids = this.projectService.createDraftData( project_id, authAccountUUID );
		log.info( "★編集開始したプロジェクト [{}:{}]", ids.id, ids.uuid );
		return ids;
	}

	@GetMapping("test/metadata/{project_id}")
	public DraftMetadata test_get_metadata(
			HttpServletRequest request,
			@PathVariable String project_id ) {
		
		// 認証ヘッダからアカウント情報を取得。
		UUID authAccountUUID = this.authorize.getAuthAccountUUID( request, true ); // 必須
		
		DraftMetadata data = this.projectService.getDraftMetadata( project_id, authAccountUUID );
		return data;
	}

	@PostMapping("test/metadata/{project_id}")
	public DraftMetadata test_update_metadata(
			HttpServletRequest request,
			@RequestBody String body,
			@PathVariable String project_id ) {
		
		DraftMetadata posted = JsonMapper.parse( body, DraftMetadata.class );
		
		// 認証ヘッダからアカウント情報を取得。
		UUID authAccountUUID = this.authorize.getAuthAccountUUID( request, true ); // 必須
		
		DraftMetadata updated = this.projectService.updateDraftMetadata( project_id, authAccountUUID, posted );
		log.info( "★更新されたデータトークン:{}", updated.token );
		return updated;
	}
	
	

	@PostMapping("test/publish/{project_id}")	
	public long test_publish(
			HttpServletRequest request,
			@PathVariable String project_id ) {

		// 認証ヘッダからアカウント情報を取得。
		UUID authAccountUUID = this.authorize.getAuthAccountUUID( request, true ); // 必須
		
		return this.projectService.requestPublishProject( project_id, authAccountUUID );
	}
	
	@Autowired
	PublishService publishService;

	@PostMapping("test/permit/{num}") 	
	public Object test_permit(
			HttpServletRequest request,
			@PathVariable long num ) {

		// 認証ヘッダからアカウント情報を取得。
		UUID authAccountUUID = this.authorize.getAuthAccountUUID( request, true ); // 必須
		
		return this.publishService.permitPublishProject( num, authAccountUUID );
		
	}
	@PostMapping("test/deny/{num}") 	
	public Object test_deny(
			HttpServletRequest request,
			@PathVariable long num ) {

		// 認証ヘッダからアカウント情報を取得。
		UUID authAccountUUID = this.authorize.getAuthAccountUUID( request, true ); // 必須
		
		return this.publishService.denyPublishProject( num, authAccountUUID );
		
	}
	
	@Autowired
	ConfigSet config;
	
	@Autowired
	FileModule nextcloud;
	
	@GetMapping("test/nc") 
	public Object test_nextcloud() {

		return JsonMapper.map()
				.put( "url", this.config.nextcloud.endpoints.URL )
				.put( "admin", config.nextcloud.client.ADMIN )
				.put( "pass", config.nextcloud.client.ADMIN_PASS )
				.stringify();
	}
	
	@PostMapping("test/nc/dir/{dir}") 
	public Object test_nextcloud_create_dir(@PathVariable String dir) {
		return nextcloud.createDirectory( dir );
	}
	@GetMapping("test/nc/dir/{dir}") 
	public Object test_nextcloud_lst (@PathVariable String dir) {
		return nextcloud.list( dir );
	}
	
	
	

	@Autowired
	ElasticSearchModule elasticsearch;
	
	
	// とりあえず :9200 の ElasticSearch に繋いでみるだけ。
	@GetMapping({ "elastic", "elastic-search", "es" })
	public String es() {
		
		return elasticsearch.information();
	}
	

	@GetMapping("es/{project_id}")
	public ESProjectIndex es_get_project(
			@PathVariable String project_id ) {
		
		var data = this.daoProject.read( project_id );
		if ( null == data ) throw new RestApiException( HttpStatus.NOT_FOUND );
		var project = new TProjectDataReader( data );
		
		var ids = project.toIds();
		log.info( "project : {}({})", ids.uuid, ids.id );
		
		ESProjectIndex esProject = elasticsearch.getProject( ids, ProjectIndexType.PUBLIC );
		
		return esProject;
	}


	@GetMapping( "es/q" )
	public String es_query() {
		
		// GETするURLを構築
		final String BASE_URL = "http://localhost:9200";
		final String url = UrlBuilder.url( BASE_URL ).path( "public-project", "_search" ).build();
		
		
		final String query = JsonMapper.map()
				// TODO：クエリ実装
				.put( "_source", new String[] {"aggregate", "listdata"} )
				.nest( "query" )
					.nest( "wildcard" )
						.put( "metadata.keyword", "*RPMM000*" )
					.peel()
				.peel()
				.stringify();
		log.debug( query );
		
		var client = new StandardRestClient();
		
		
		var api = client.post( url, query );
		log.debug( "code:{}", api.response.status );
		log.debug( "text:{}", api.response.text );
		
		
		if ( api.response.not2xxSuccessful() ) {
			log.error( "ElasticSearch API Error {} {}"
					, api.response.status
					, api.response.text );
			throw new RestApiException( HttpStatus.INTERNAL_SERVER_ERROR );
		}
		return api.response.body;
	}
	@GetMapping( "es/p/{from}/{size}" )
	public String es_paging(@PathVariable long from, @PathVariable long size) {
		
		// GETするURLを構築
		final String BASE_URL = "http://localhost:9200";
		final String url = UrlBuilder.url( BASE_URL ).path( "public-project", "_search" ).build();
		
		
		final String query = JsonMapper.map()
				.put( "_source", new String[] {"aggregate", "listdata"} )
				// 本当はここで query が必要。
				.put( "from", from )
				.put( "size", size )
//				.nest( "sort" )
//					.nest( "ids.id" )
//						.put( "order", "asc" )
//					.peel()
//				.peel()
				.stringify();
		log.debug( query );
		
		var client = new StandardRestClient();
		
		
		var api = client.post( url, query );
		log.debug( "code:{}", api.response.status );
		log.debug( "text:{}", api.response.text );
		
		
		if ( api.response.not2xxSuccessful() ) {
			log.error( "ElasticSearch API Error {} {}"
					, api.response.status
					, api.response.text );
			throw new RestApiException( HttpStatus.INTERNAL_SERVER_ERROR );
		}
		return api.response.body;
	}


	@GetMapping( "es/q/{word}" )
	public String es_query(@PathVariable String word) {
		
		// GETするURLを構築
		final String BASE_URL = "http://localhost:9200";
		final String url = UrlBuilder.url( BASE_URL ).path( "public-project", "_search" ).build();
		
		
		final String query = JsonMapper.map()
				// TODO：クエリ実装
				.put( "_source", new String[] {"aggregate", "listdata"} )
				.nest( "query" )
					.nest( "wildcard" )
						.put( "metadata.keyword", "*" + word + "*" )
					.peel()
				.peel()
				.stringify();
		log.debug( query );
		
		var client = new StandardRestClient();
		
		
		var api = client.post( url, query );
		log.debug( "code:{}", api.response.status );
		log.debug( "text:{}", api.response.text );
		
		
		if ( api.response.not2xxSuccessful() ) {
			log.error( "ElasticSearch API Error {} {}"
					, api.response.status
					, api.response.text );
			throw new RestApiException( HttpStatus.INTERNAL_SERVER_ERROR );
		}
		return api.response.body;
	}

	

	@GetMapping("tmp")
	public String tmp() {

		var schema = List.of(  TemplateJson.schema() );
		List<String> has_species_name = new ArrayList<>();
		
		for ( var sheet : schema ) {
			// シート定義に species_name 項目を含むものを抽出。
			for ( var column : sheet.contents ) {
				if ( "species_name".equals( column.label_id ) ) {
					has_species_name.add( sheet.sheet_id );
					log.info( "◆{}", sheet.sheet_id );
					continue;
				}
			}
		}
		
		String json = JsonMapper.stringify( has_species_name );
		return json;
	}
	
	
	
	
	@GetMapping("pulldown")
	public String get_pulldown_items() {
		
		String[] projectIds = {
//				"RMM00001", 
//				"RMM00002", 
//				"RMM00003", 
//				"RMM00004", 
//				"RMM00005", 
//				"RMM00006", 
//				"RMM00007", 
//				"RMM00008", 
//				"RMM00009", 
//				"RMM00010", 
//				"RMM00011", 
//				"RMM00012", 
//				"RMM00013", 
//				"RMM00014", 
//				"RMM00015", 
//				"RMM00016", 
//				"RMM00017", 
//				"RMM00018", 
//				"RMM00019", 
//				"RMM00020", 
				"RPMM0001", 
				"RPMM0002", 
				"RPMM0003", 
				"RPMM0004", 
				"RPMM0005", 
				"RPMM0006", 
				"RPMM0007", 
				"RPMM0008", 
				"RPMM0010", 
				"RPMM0011", 
				"RPMM0012", 
				"RPMM0013", 
				"RPMM0014", 
				"RPMM0015", 
				"RPMM0016", 
				"RPMM0017", 
				"RPMM0018", 
				"RPMM0019", 
				"RPMM0020", 
				"RPMM0021", 
				"RPMM0023", 
				"RPMM0024", 
				"RPMM0025", 
				"RPMM0026", 
				"RPMM0027", 
				"RPMM0028", 
				"RPMM0029", 
				"RPMM0030", 
				"RPMM0031", 
				"RPMM0032", 
				"RPMM0033", 
				"RPMM0034", 
				"RPMM0035", 
				"RPMM0036", 
				"RPMM0037", 
				"RPMM0038", 
				"RPMM0039", 
				"RPMM0040", 
				"RPMM0041", 
				"RPMM0042", 
				"RPMM0043", 
				"RPMM0045", 
				"RPMM0046", 
				"RPMM0047", 
				"RPMM0048", 
				"RPMM0049", 
				"RPMM0050", 
				"RPMM0051", 
				"RPMM0052", 
				"RPMM0053", 
				"RPMM0054", 
				"RPMM0055", 
				"RPMM0056", 
				"RPMM0057", 
				"RPMM0058", 
				"RPMM0059", 
				"RPMM0060", 
				"RPMM0061", 
				"SE1", 
				"SE10", 
				"SE100", 
				"SE101", 
				"SE102", 
				"SE103", 
				"SE104", 
				"SE105", 
				"SE106", 
				"SE107", 
				"SE108", 
				"SE109", 
				"SE11", 
				"SE112", 
				"SE113", 
				"SE114", 
				"SE115", 
				"SE116", 
				"SE117", 
				"SE118", 
				"SE119", 
				"SE12", 
				"SE120", 
				"SE121", 
				"SE122", 
				"SE123", 
				"SE13", 
				"SE14", 
				"SE15", 
				"SE16", 
				"SE169", 
				"SE17", 
				"SE170", 
				"SE171", 
				"SE172", 
				"SE18", 
				"SE189", 
				"SE19", 
				"SE2", 
				"SE20", 
				"SE22", 
				"SE25", 
				"SE26", 
				"SE27", 
				"SE28", 
				"SE29", 
				"SE3", 
				"SE30", 
				"SE31", 
				"SE32", 
				"SE33", 
				"SE34", 
				"SE35", 
				"SE36", 
				"SE37", 
				"SE39", 
				"SE4", 
				"SE40", 
				"SE41", 
				"SE42", 
				"SE5", 
				"SE6", 
				"SE61", 
				"SE62", 
				"SE63", 
				"SE64", 
				"SE65", 
				"SE66", 
				"SE67", 
				"SE68", 
				"SE69", 
				"SE7", 
				"SE70", 
				"SE71", 
				"SE72", 
				"SE73", 
				"SE74", 
				"SE75", 
				"SE76", 
				"SE77", 
				"SE78", 
				"SE79", 
				"SE8", 
				"SE80", 
				"SE81", 
				"SE82", 
				"SE83", 
				"SE84", 
				"SE85", 
				"SE86", 
				"SE87", 
				"SE88", 
				"SE89", 
				"SE9", 
				"SE90", 
				"SE91", 
				"SE92", 
				"SE93", 
				"SE94", 
				"SE95", 
				"SE96", 
				"SE97", 
				"SE98", 
				"SE99", 
		};

		HashSet<String> species = new HashSet<String>();
		HashSet<String> devices = new HashSet<String>();
		
		var project = new TProjectDataReader();
		for ( String id : projectIds ) {
			var data = this.daoProject.read( id );
			project.bind( data );
			
			String json = project.metadataJson();
			MetadataJson metadata = MetadataJson.parse( json );
			
			if ( null == metadata ) continue;
			
			var extract = MetadataExtract.execute( metadata );
			for ( String x : extract.species() ) {
				species.add( x );
			}
			for ( String x : extract.devices() ) {
				devices.add( x );
			}
		}
		
		String[] sp = species.toArray( String[]::new );
		String[] dv = devices.toArray( String[]::new );
		
		return JsonMapper.map()
				.put( "species", sp )
				.put( "devices", dv )
				.stringify();
	}
	
}
