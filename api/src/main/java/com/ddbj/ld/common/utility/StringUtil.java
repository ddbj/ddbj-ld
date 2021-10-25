package com.ddbj.ld.common.utility;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;


/**
 * 文字列操作系のユーティリティ.
 * 
 * @author sugaryo
 */
public class StringUtil {
	
	public static String timestamp() {
		final DateTimeFormatter f = DateTimeFormatter.ofPattern( "yyyyMMdd_HHmmss" );
		return LocalDateTime.now().format( f );
	}
	
	public static String uuidv4() {
		return UUID.randomUUID().toString();
	}
	
	public static UUID uuidv4(String uuid) {
		return UUID.fromString( uuid );
	}
	
	public static UUID uuidv3(String name) {
		return UUID.nameUUIDFromBytes( name.getBytes() );
	}
	
	/**
	 * @param string 文字列
	 * @return {@code null} を 空文字列 {@code ""} に置き換えた値。
	 */
	public static String nvl( final String string ) {
		return null == string ? "" : string;
	}
	
	public static final String[] EMPTY_STRINGS = {};
	/**
	 * @param strings 文字列配列
	 * @return {@code null} を 要素数０の配列 {@value #EMPTY_STRINGS} に置き換えた値。
	 */
	public static String[] nvl( final String[] strings ) {
		return null == strings ? EMPTY_STRINGS : strings;
	}
	
	/**
	 * @param string 文字列
	 * @return 文字列が {@code null} または 空文字列 {@code ""} の場合は {@code true} を、<br>
	 *         それ以外の場合は {@code false} を返します。
	 * 
	 * @see String#isEmpty()
	 */
	public static boolean isNullOrEmpty( final String string ) {
		return null == string || string.isEmpty();
	}

	/**
	 * @param string 文字列
	 * @return {@code !isNullOrEmpty( string )}
	 *
	 * @see #isNullOrEmpty(String)
	 * @see String#isEmpty()
	 */
	public static boolean notNullOrEmpty( final String string ) {
		return !isNullOrEmpty( string );
	}
	
	/**
	 * @param string 文字列
	 * @return 指定した文字列をBASE64エンコードした値。
	 * 
	 * @see #nvl(String)
	 */
	public static String base64( final String string ) {
		return Base64.getEncoder().encodeToString( nvl( string ).getBytes( StandardCharsets.UTF_8 ) );
	}
	
	/**
	 * @param separator 区切り文字
	 * @param strings   文字列群
	 * @return 指定した文字列群を、指定した区切り文字で結合した値。
	 */
	public static String join( final String separator, final String[] strings ) {
		return join( null, separator, null, Arrays.asList( strings ) );
	}
	
	/**
	 * @param separator 区切り文字
	 * @param strings   文字列群
	 * @return 指定した文字列群を、指定した区切り文字で結合した値。
	 */
	public static String join( final String separator, final Iterable<String> strings ) {
		return join( null, separator, null, strings );
	}
	
	
	/**
	 * @param bracketBegin 括り文字（開始）
	 * @param separator    区切り文字
	 * @param bracketEnd   括り文字（終了）
	 * @param strings      文字列群
	 * @return 指定した文字列群を、指定した区切り文字で結合し、括り文字で括った文字列。
	 */
	public static String join( final String bracketBegin, final String separator, final String bracketEnd,
			final String[] strings ) {
		return join( bracketBegin, separator, bracketEnd, Arrays.asList( strings ) );
	}
	
	/**
	 * @param bracketBegin 括り文字（開始）
	 * @param separator    区切り文字
	 * @param bracketEnd   括り文字（終了）
	 * @param strings      文字列群
	 * @return 指定した文字列群を、指定した区切り文字で結合し、括り文字で括った文字列。
	 */
	public static String join( final String bracketBegin, final String separator, final String bracketEnd,
			final Iterable<String> strings ) {
		StringBuilder sb = new StringBuilder();
		
		sb.append( null == bracketBegin ? "" : bracketBegin );
		
		String add = "";
		for ( String string : strings ) {
			sb.append( add );
			sb.append( nvl( string ) );
			
			add = separator;
		}
		
		sb.append( null == bracketEnd ? "" : bracketEnd );
		
		return sb.toString();
	}

	/**
	 * @param source   不要な部分に削除したい文字列　
	 * @param targets  削除する文字列の配列
	 * @return 不要な部分を削除した文字列
	 */
	public static String remove(final String source, final String... targets) {
		var result = source;

		for (String target : targets) {
			result = result.replaceAll(target, "");
		}

		return result;
	}

	/**
	 * @param sources   不要な部分に削除したい文字列の配列
	 * @param targets  削除する文字列の配列
	 * @return 不要な部分を削除した文字列の配列
	 */
	public static String[] removeAll(final String[] sources, final String... targets) {

		var results = new ArrayList<String>();

		for (String source : sources) {
			results.add(remove(source, targets));
		}

		return results.toArray(String[]::new);
	}
}

