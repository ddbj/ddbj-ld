package com.ddbj.ld.common.utility.api;

import com.ddbj.ld.common.constant.ApiMethod;
import com.ddbj.ld.data.value.RestApi;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;


/**
 * {@code REST API}用 {@code HTTP Client} インタフェース.
 * 
 * <p>
 * <b>■概要：</b><br>
 * {@link RestClient} 部品のプロキシ実装を作成するためのインタフェース宣言。<br>
 * 基本的な {@code HTTP Client} としての実装は部品を使用し、独自のアダプタを差し込む為の拡張ポイントとして使用する。
 * </p>
 * 
 * @author sugaryo
 * 
 * @see org.springframework.web.client.RestTemplate
 * @see <a href="https://gist.github.com/sugaryo/da52fac8a661374df409a849ccc750d1">sugaryo.gist - RestClient Utility</a>
 */
public interface IRestClient {
	
	/**
	 * API実行
	 * 
	 * @param url URL
	 * @param method HTTPリクエストメソッド
	 * @param headers HTTPリクエストヘッダ<br>
	 * 		（特に指定するヘッダが無い場合は {@code null} を指定できます）
	 * @param data 送信するデータ<br>
	 * 		（特に送信するデータが無い場合は {@code null} を指定できます）
	 * @return API実行結果（リクエスト情報とレスポンス情報）
	 */
	RestApi exchange(String url, ApiMethod method, Map<String, String> headers, String data );
	
	
	/**
	 * @param url URL文字列
	 * @return {@link URI} インスタンス
	 */
	public static URI uri( String url ) {
		// コードを汚すだけの検査例外をランタイム例外でラップして再スローするだけ。
		try {
			return new URI( url );
		} catch ( URISyntaxException ex ) {
			throw new RuntimeException( ex );
		}
	}
}
