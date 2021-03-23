package ddbjld.api.data.values;

import java.util.UUID;

import ddbjld.api.common.utility.JsonMapper;

public class ProjectIds {
	/** t_project.uuid */
	public final UUID uuid; // PK
	/** t_project.id */
	public final String id; // "RMM#00000"
	
	ProjectIds(){
		// jackson用のデフォコン
		this( null, null );
	}
	public ProjectIds( UUID uuid, String id ) {
		this.uuid = uuid;
		this.id = id;
	}
	
	public static ProjectIds parse(final String json ) {
		return JsonMapper.parse( json, ProjectIds.class );
	}
}
