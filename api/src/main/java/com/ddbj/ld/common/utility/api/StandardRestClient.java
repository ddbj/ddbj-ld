package com.ddbj.ld.common.utility.api;

import com.ddbj.ld.common.constant.ApiMethod;
import com.ddbj.ld.data.value.RestApi;

import java.util.Map;


/**
 * @author sugaryo
 * @see <a href="https://gist.github.com/sugaryo/da52fac8a661374df409a849ccc750d1">sugaryo.gist - RestClient Utility</a>
 */
public class StandardRestClient extends RestClient {
	
	// GET
	
	public RestApi get(
			String url ) {
		return this.get( url, null, null );
	}
	public RestApi get(
			String url,
			Map<String, String> headers ) {
		return this.get( url, headers, null );
	}
	public RestApi get(
			String url,
			String data ) {
		return this.get( url, null, data );
	}
	
	public RestApi get(
			String url,
			Map<String, String> headers,
			String data ) {
		return super.exchange( url, ApiMethod.GET, headers, data );
	}
	
	// GET bin
	
	public RestApi bin(
			String url ) {
		return this.bin( url, null, null );
	}
	public RestApi bin(
			String url,
			Map<String, String> headers ) {
		return this.bin( url, headers, null );
	}
	public RestApi bin(
			String url,
			String data ) {
		return this.bin( url, null, data );
	}
	
	public RestApi bin(
			String url,
			Map<String, String> headers,
			String data ) {
		return super.exchange( url, ApiMethod.BINARY_GET, headers, data );
	}
	
	// POST
	
	public RestApi post(
			String url,
			String data ) {
		return this.post( url, null, data );
	}
	
	public RestApi post(
			String url,
			Map<String, String> headers,
			String data ) {
		return super.exchange( url, ApiMethod.POST_JSON, headers, data );
	}
	
	// PUT
	
	public RestApi put(
			String url,
			String data ) {
		return this.put( url, null, data );
	}
	
	public RestApi put(
			String url,
			Map<String, String> headers,
			String data ) {
		return super.exchange( url, ApiMethod.PUT, headers, data );
	}
	
	// PATCH
	
	public RestApi patch(
			String url,
			String data ) {
		return this.patch( url, null, data );
	}
	
	public RestApi patch(
			String url,
			Map<String, String> headers,
			String data ) {
		return super.exchange( url, ApiMethod.PATCH, headers, data );
	}
	
	// DELETE
	
	public RestApi delete(
			String url ) {
		return this.delete( url, null, null );
	}
	public RestApi delete(
			String url,
			Map<String, String> headers ) {
		return this.delete( url, headers, null );
	}
	public RestApi delete(
			String url,
			String data ) {
		return this.delete( url, null, data );
	}
	
	public RestApi delete(
			String url,
			Map<String, String> headers,
			String data ) {
		return super.exchange( url, ApiMethod.DELETE, headers, data );
	}
}
