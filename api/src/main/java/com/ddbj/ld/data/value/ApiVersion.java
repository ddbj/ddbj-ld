package com.ddbj.ld.data.value;

import com.ddbj.ld.common.constant.ApiStatus;

public class ApiVersion {
	public final String version;
	public final ApiStatus status;
	
	public ApiVersion( String version, ApiStatus status ) {
		this.version = version;
		this.status = status;
	}
}
