package ddbjld.api.common.utility;

import javax.servlet.http.HttpServletRequest;

/**
 * ヘッダに対する処理を行うUtil.
 *
 * @author m.tsumura
 *
 **/
public class HeaderUtil {
    private static final String BEARER        = "Bearer ";
    private static final String AUTHORIZATION = "Authorization";
    private static final String X_MB_SECRET   = "X-MB-Secret";

    /**
     * Authorizationヘッダを取得するメソッド.
     *
     * @param request
     *
     * @return Authorizationヘッダ
     *
     **/
    public static String getAuthorization(final HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION);
    }
    /**
     * @param request HTTPリクエスト
     * @return {@value #X_MB_SECRET} ヘッダ
     */
<<<<<<< HEAD
    public static String getSecretKey(final HttpServletRequest request) {
=======
    public static String getMtabobankSecretKey(final HttpServletRequest request) {
>>>>>>> 差分修正
        return request.getHeader(X_MB_SECRET);
    }

    /**
     * AuthorizationヘッダにBearerがあるかチェックするメソッド.
     *
     * @param header
     *
     * @return 判定結果
     *
     **/
    public static boolean existsAuthorizationHeader(final String header) {
        return null != header && header.startsWith(BEARER);
    }

    /**
     * Authorizationヘッダからアクセストークンを取得するメソッド.
     *
     * @param header
     *
     * @return アクセストークン
     *
     **/
    public static String getAccessToken(final String header) {
        return header.substring(BEARER.length());
    }

    /**
     * リクエストからアクセストークンを取得するメソッド.
     *
     * @param request
     *
     * @return アクセストークン
     *
     **/
    public static String getAccessToken(final HttpServletRequest request) {
    	
        String header = getAuthorization(request);
		
		if ( null == header ) return null;
		
		if ( existsAuthorizationHeader( header ) ) {
			return getAccessToken( header );
		} else {
			return null;
		}
    }
}
