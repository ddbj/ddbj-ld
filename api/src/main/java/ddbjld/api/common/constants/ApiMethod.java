package ddbjld.api.common.constants;


/**
 * {@code REST API} を使用する際に HttpClient で使用する {@code HTTP Method} を定義します。
 * 
 * @author sugaryo
 * @see <a href="https://gist.github.com/sugaryo/da52fac8a661374df409a849ccc750d1">sugaryo.gist - RestClient Utility</a>
 */
public enum ApiMethod {
	
	GET,
	POST_JSON,
	POST_X_FORM_URLENCODED,
	PATCH,
	PUT,
	DELETE,
	
	BINARY_GET,
}
