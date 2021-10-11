package com.ddbj.ld.app.controller.handler;

import com.ddbj.ld.common.exception.RestApiException;
import com.ddbj.ld.common.utility.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestHandler {
	
	@ExceptionHandler
	private ResponseEntity<String> onError( Exception ex ) {
		
		final HttpStatus status = ex instanceof RestApiException
				? ((RestApiException)ex).status
				: HttpStatus.INTERNAL_SERVER_ERROR;
		
		log.error( status.getReasonPhrase(), ex );
		
		// クライアントには完結なエラー情報だけを返す。
		String json = JsonMapper.map()
				.put( "code", status.value() )
				.put( "message", status.getReasonPhrase() )
				.stringify();
		return new ResponseEntity<String>( json, status );
	}
}
