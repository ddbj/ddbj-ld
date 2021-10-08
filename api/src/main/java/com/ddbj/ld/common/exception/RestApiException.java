package com.ddbj.ld.common.exception;

import org.springframework.http.HttpStatus;

public class RestApiException extends RuntimeException {
	
	/** default serial version */
	private static final long serialVersionUID = 1L;
	
	public final HttpStatus status;
	
	// TODO：ステータスだけじゃなく、システム固有メッセージやエラーに対するヒントも載せたほうが良い。
	
	public RestApiException( HttpStatus status ) {
		super( status.getReasonPhrase() );
		this.status = status;
	}
}
