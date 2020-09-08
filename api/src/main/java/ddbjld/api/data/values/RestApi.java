package ddbjld.api.data.values;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;

import ddbjld.api.common.utility.JsonMapper;


/**
 * REST API 実行結果セット（Request/Response）.
 * 
 * @author sugaryo
 * 
 * @see sugaryo.demo.elasticsearch.common.util.IRestClient
 * @see sugaryo.demo.elasticsearch.common.util.RestClient
 * @see <a href="https://gist.github.com/sugaryo/da52fac8a661374df409a849ccc750d1">sugaryo.gist - RestClient Utility</a>
 */
public class RestApi {

	public static class RestRequest {
		public final String url;
		public final String method;
		public final String headers;
		public final String body;
		
		public RestRequest( String url, String method, String headers, String body ) {
			this.url = url;
			this.method = method;
			this.headers = headers;
			this.body = body;
		}
	}
	
	public static class RestResponse {
		public final int status;
		public final String text;
		public final String headers;
		public final String body;
		
		public boolean is2xxSuccessful() {
			return 200 <= this.status && this.status < 300;
		}
		public boolean not2xxSuccessful() {
			return !this.is2xxSuccessful();
		}

		public RestResponse( int status, String text, String headers, String body ) {
			this.status = status;
			this.text = text;
			this.headers = headers;
			this.body = body;
		}
	}
	
	@Deprecated // 今回はこれは使わないと思われる。
	public static class BinaryResponse extends RestResponse {
		public final byte[] bin;
		public BinaryResponse(int status, String text, String headers, byte[] bin) {
			super( status, text, headers, "" );
			this.bin = bin;
		}
	}

	public final RestRequest request;
	public final RestResponse response;
	
	public RestApi(
			RestRequest request, 
			RestResponse response ) {
		
		this.request = request;
		this.response = response;
	}
	
	
	/**
	 * REST API 実行結果セットのコンバータ.
	 * 
	 * @author sugaryo
	 *
	 * @see org.springframework.http.RequestEntity
	 * @see org.springframework.http.ResponseEntity
	 */
	public static class Converter {

		/**
		 * {@code SpringBoot系} の {@link RequestEntity} を {@link RestApi.RestRequest} に変換します。
		 */
		public static class Request {
			
			public static final RestRequest fromVoid(RequestEntity<Void> entity) {
				String url = entity.getUrl().getPath();
				String method = entity.getMethod().name();
				String headers = JsonMapper.stringify( entity.getHeaders() );
				String body = null;
				
				return new RestRequest( url, method, headers, body );
			}
			
			public static final RestRequest fromString(RequestEntity<String> entity) {
				String url = entity.getUrl().getPath();
				String method = entity.getMethod().name();
				String headers = JsonMapper.stringify( entity.getHeaders() );
				String body = entity.getBody();
				
				return new RestRequest( url, method, headers, body );
			}
			
			public static RestRequest fromPatchEntity(String url, HttpEntity<String> entity) {
				String method = HttpMethod.PATCH.name();
				String headers = JsonMapper.stringify( entity.getHeaders() );
				String body = entity.getBody();
				
				return new RestRequest( url, method, headers, body );
			}
		}
		
		/**
		 * {@code SpringBoot系} の {@link ResponseEntity} を {@link RestApi.RestResponse} に変換します。
		 */
		public static class Response {
			
			public static final RestResponse fromEntity(ResponseEntity<String> entity) {
				
				int status = entity.getStatusCode().value();
				String text = entity.getStatusCode().name();
				String body = entity.getBody();
				String headers = JsonMapper.stringify( entity.getHeaders() );
				
				return new RestResponse( status, text, headers, body );
			}

			public static final RestResponse fromBinary( ResponseEntity<byte[]> entity ) {

				int status = entity.getStatusCode().value();
				String text = entity.getStatusCode().name();
				byte[] bin = entity.getBody();
				String headers = JsonMapper.stringify( entity.getHeaders() );
				
				return new BinaryResponse( status, text, headers, bin );
			}
			
			public static final RestResponse fromClientError(RestClientResponseException ex) {
				// RestTemplateのDefaultErrorHandler では、
				// HTTPの 2xx系 以外は例外として飛ばして来るので、
				// HTTP系エラーの場合はトラップして正常ルートで返す。
				// HTTP系のエラーは以下の３パターン。
				// ■4xx - RestClientResponseException -> HttpStatusCodeException -> HttpClientErrorException
				// ■5xx - RestClientResponseException -> HttpStatusCodeException -> HttpServerErrorException
				// ■xxx - RestClientResponseException -> UnknownHttpStatusCodeException
				// ※ただし、
				// 現在の RestClient 共通部品ではデフォルトエラーハンドラを使用せず、
				// 正常ルートで処理しているため 4xx/5xx 系は例外にはならない。
				int status = ex.getRawStatusCode();
				String text = ex.getStatusText();
				String body = ex.getMessage();
				String headers = JsonMapper.stringify( ex.getResponseHeaders() );
				
				return new RestResponse( status, text, headers, body );
			}
			
			public static final RestResponse fromException(Exception ex) {
				int status = -1;
				String text = ex.getMessage();
				String body = "";
				String headers = "";
				
				return new RestResponse( status, text, headers, body );
			}
		}
	
	}
}
