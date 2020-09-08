package ddbjld.api.app.controller.handler;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ddbjld.api.app.service.AuthService;
import ddbjld.api.common.annotation.Auth;
import ddbjld.api.common.utility.HeaderUtil;

/**
 * 認証が必要なエンドポイント用の前処理.
 *
 * エンドポイントに@Authがつけられていると認証をチェックする
 *
 * @author m.tsumura
 *
 **/
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private AuthService authService;

    /** @inherit */
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler)
            throws Exception {

    	// コントローラメソッドの場合は @Auth アノテートを確認して認証処理を被せる
		if (handler instanceof HandlerMethod) {
			return authorize(request, response, (HandlerMethod)handler);
		}
		// HandlerMethod 以外（静的リソース系の場合がある）は素通し
		else {
			return true;
		}
    }

    /**
     * 認証されているか判定するメソッド.
     *
     * 認証が必要でないエンドポイントはスルーする
     *
     * @param request
     * @param response
     * @param handlerMethod
     *
     * @return 判定結果
     *
     **/
	private boolean authorize( 
			HttpServletRequest request, 
			HttpServletResponse response, 
			HandlerMethod handlerMethod ) throws IOException {
		
        Method method = handlerMethod.getMethod();

        Auth auth = AnnotationUtils.findAnnotation(method, Auth.class);

        if(null == auth) {
            /// @Authが指定されていないコントローラメソッドならノーチェック
            return true;
        }

        String accessToken = HeaderUtil.getAccessToken(request);

        if (null == accessToken) {
            // 認証ヘッダがつけられていないなら400応答
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);

            return false;
        }

        if (authService.isAuth(accessToken)) {
            // Trueなら何もしない
        } else {
            // 認証されていないなら401応答
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);

            return false;
        }

        return true;
	}

}
