package ddbjld.api.maintenance;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ddbjld.api.app.core.module.AuthModule;
import ddbjld.api.common.utility.JsonMapper;
import ddbjld.api.common.utility.StringUtil;
import ddbjld.api.data.values.DraftMetadata;
import ddbjld.api.data.values.ProjectIds;

@RestController
@RequestMapping("system/maintenance")
@Slf4j
public class SystemMaintenanceController {
	
	@Autowired
	SystemMaintenanceService service;
	
	@Autowired
	AuthModule auth;


	// FIXME：後でConfigSetにSystemConfig作って移動。
	@Value("${ddbj.system.maintenance.account.uuid}")
	UUID maintenanceAccountUUID;
	
	@GetMapping("keygen/{phrase}")
	public UUID keygen(
			@PathVariable String phrase) {
		
		return StringUtil.uuidv3( phrase );
	}
	
	@PostMapping("auth")
	public String authorize(HttpServletRequest request) {

		this.auth.requireSecretKey( request );
		
		return "OK.";
	}
	
	// 初期データ投入用
	@RequestMapping( path = "data/project/{project_id}", 
			method = { 
					RequestMethod.POST, 
					RequestMethod.PUT, 
			} )
	public ProjectIds entryProjectData( 
			HttpServletRequest request,
			@PathVariable String project_id,
			@RequestBody String json ) {
		
		log.info( "★データ登録：Project[{}]", project_id );
		
		this.auth.requireSecretKey( request );
		
		DraftMetadata data = JsonMapper.parse( json, DraftMetadata.class );
		var ids = this.service.putProjectData( this.maintenanceAccountUUID, project_id, data.metadata );
		log.info( "ids:{}({})", ids.uuid, ids.id );
		return ids;
	}

	
	@PostMapping("data/facet-item/refresh")
	public String refreshFacetItems( HttpServletRequest request ) {

		log.info( "★DB登録済みプロジェクトデータから検索用データ集計" );
		this.auth.requireSecretKey( request );
		
		this.service.refreshFacetItems();
		
		return "OK.";
	}
}
