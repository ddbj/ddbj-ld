package ddbjld.api.data.values;

import ddbjld.api.common.constants.ApiStatus;

public class ApiVersion {
	public final String version;
	public final ApiStatus status;
	
	public ApiVersion( String version, ApiStatus status ) {
		this.version = version;
		this.status = status;
	}
}
