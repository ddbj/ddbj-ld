package ddbjld.api.app.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import ddbjld.api.common.exceptions.RestApiException;
import ddbjld.api.common.utility.JsonMapper;

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
