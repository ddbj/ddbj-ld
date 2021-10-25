package com.ddbj.ld.common.utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.util.HashMap;
import java.util.Map;


/**
 * {@link ObjectMapper} のラップユーティリティ.
 * 
 * <p>
 * <b>■クラス概要：</b><br>
 * {@link ObjectMapper} の各種JSON-Object変換メソッドをラップし、
 * 検査例外をRuntime例外に置き換えただけのQuietな薄いWrapperです。<br>
 * 使用する {@link ObjectMapper} インスタンスは {@link SingletonHolder} でキャッシュします。
 * </p>
 * 
 * @author sugaryo
 * 
 * @see ObjectMapper
 */
public class JsonMapper {

	/**
	 * JSON文字列を指定した型にパースします。
	 *
	 * @param <T>  変換する型
	 * @param json 変換するJSON文字列
	 * @param type 変換する型の{@link Class}
	 * @return {@link ObjectMapper#readValue(String, Class)}
	 */
	public static <T> T parse( final String json, final Class<?> type ) {
		try {
			@SuppressWarnings("unchecked")
			T obj = (T) SingletonHolder.mapper.readValue( json, type );
			return obj;
		} catch ( Exception ex ) {
			throw new RuntimeException( ex );
		}
	}

	/**
	 * JSON文字列を指定したジェネリック型にパースします。
	 *
	 * @param <T>  変換するジェネリック型
	 * @param json 変換するJSON文字列
	 * @param ref  変換するジェネリック型の{@link TypeReference}
	 * @return {@link ObjectMapper#readValue(String, TypeReference)}
	 */
	public static <T> T parse( final String json, final TypeReference<T> ref ) {
		try {
			return SingletonHolder.mapper.readValue( json, ref );
		} catch ( Exception ex ) {
			throw new RuntimeException( ex );
		}
	}
	
	/**
	 * 指定したオブジェクトをJSON文字列に変換します。
	 * 
	 * @param obj JSON文字列化するオブジェクト
	 * @return {@link ObjectMapper#writeValueAsString(Object)}
	 */
	public static String stringify( Object obj ) {
		try {
			return SingletonHolder.mapper.writeValueAsString( obj );
		} catch ( Exception ex ) {
			throw new RuntimeException( ex );
		}
	}
	
	
	/**
	 * {@link Map<String, Object>} のラッパーユーティリティ。
	 * 
	 * <ui>
	 * <li>{@link Map#put} をラップして {@link #put(String, Object) putメソッドチェーン} を提供します。
	 * <li>{@link JsonMapper#stringify(Object)} をラップして {@link #stringify() MapのJSON文字列化} を提供します。
	 * </ul>
	 * 
	 * @author sugaryo
	 */
	public static class MapContext {

		private final Map<String, Object> map = new HashMap<>();
		private MapContext parent = null;

		/**
		 * {@link Map<String,Object>#put} のメソッドジェーン
		 * 
		 * @param key {@link Map#put} の {@code key}
		 * @param obj {@link Map#put} の {@code value}
		 * @return {@code this}
		 */
		public MapContext put( String key, Object obj ) {
			this.map.put( key, obj );
			return this;
		}
		
		/**
		 * ネストしたMapオブジェクトの登録
		 * 
		 * @param key putするキー
		 * @return 内包する {@link Map<String, Object>} インスタンスに指定した {@code key} で新しい {@link Map<String, Object>} を登録し、<br>
		 *         新しい {@link Map<String, Object>} インスタンスに対する {@link MapContext} を返します。
		 *         
		 * @see HashMap#put(Object, Object)
		 */
		public MapContext nest(String key) {
			
			var nested = new MapContext();
			this.map.put( key, nested.map ); // 新しいcontextを生成し this.map に nested.map を登録する。
			nested.parent = this;
			return nested;
		}
		/**
		 * ネストしたオブジェクトの親オブジェクト参照を返す。
		 * 
		 * @return {@link #nest(String)} で登録した {@link MapContext} の <b>親</b> にあたる {@link MapContext} を返します。<br>
		 *         最上位の（これ以上親がない） {@link MapContext} だった場合は何もせず {@code this} を返します。
		 */
		public MapContext peel() {
			return null == this.parent ? this : this.parent;
		}
		
		/**
		 * @return {@link JsonMapper#stringify(Object)}
		 */
		public String stringify() {
			return JsonMapper.stringify( this.map );
		}
		
		/** @inherit */
		@Override
		public String toString() {
			return this.map.toString();
		}
	}
	
	/**
	 * {@code new MapContext()} のショートカット
	 * 
	 * @return 新しい {@link MapContext} インスタンス
	 */
	public static MapContext map() {
		return new MapContext();
	}
	
	
	/**
	 * {@link ObjectMapper} の遅延初期化シングルトンホルダ。
	 * 
	 * @author sugaryo
	 */
	private static final class SingletonHolder {
		
		/** Initialization-on-demand-holder idiom */
		private static final ObjectMapper mapper = new ObjectMapper()
				.setPropertyNamingStrategy( PropertyNamingStrategy.SNAKE_CASE )
				// FIXME：暫定対応　OpenAMのAPIのためにコンバート後のJavaクラスにプロパティが存在しない場合は無視するようにしたが、OpenAMが不使用になれば不要となる予定
				.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
		
	}
}
