package com.ddbj.ld.common.constant;

public enum ApiStatus {
	
	ACTIVE("有効"),
	
	DEPRECATED("廃止予定"),
	
	UNSUPPORTED("廃止"),
	
	;
	public final String text;

	private ApiStatus( String text ) {
		this.text = text;
	}
	
}
