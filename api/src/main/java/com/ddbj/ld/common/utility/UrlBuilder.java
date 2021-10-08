package com.ddbj.ld.common.utility;

/**
 * URLパス文字列ビルダ.
 * 
 * <p>
 * <b>■概要：</b><br>
 * URLパス区切り文字 {@code "/"} を内部で自動的に補完しながら、パスを追記していく文字列編集ユーティリティです。
 * </p>
 * 
 * @author sugaryo
 * 
 * @see StringBuilder
 * @see <a href="https://gist.github.com/sugaryo/982ce13f787dde7275ac079addc6f53e">sugaryo.gist - String Utility</a>
 */
public class UrlBuilder {

	private final StringBuilder sb = new StringBuilder();
	private String join = "";


	/** default ctor. */
	public UrlBuilder() {
		super();
	}

	/**
	 * @see #path(String)
	 */
	public UrlBuilder( String node ) {
		super();
		this.path( node );
	}

	/**
	 * @see #path(String...)
	 */
	public UrlBuilder( String... nodes ) {
		super();
		this.path( nodes );
	}



	/**
	 * ノード追記.
	 *
	 * @param nodes 追記するノード
	 * @return {@code this}
	 */
	public UrlBuilder path( String... nodes ) {
		for ( String node : nodes ) {
			this.path( node );
		}
		return this;
	}

	/**
	 * ノード追記.
	 *
	 * @param node ノード
	 * @return {@code this}
	 */
	public UrlBuilder path( String node ) {
		this.sb.append( this.join );
		this.sb.append( node );
		this.join = "/";
		return this;
	}


	/**
	 * @return {@link StringBuilder#toString()}
	 */
	public String build() {
		return this.sb.toString();
	}
	
	// static method.
	
	/**
	 * {@link #UrlBuilder(String)} のショートカット。
	 * @param nodes
	 * @return {@link UrlBuilder} 
	 */
	public static UrlBuilder url( String node ) {
		return new UrlBuilder( node );
	}
	/**
	 * {@link #UrlBuilder(String...)} のショートカット。
	 * @param nodes
	 * @return {@link UrlBuilder} 
	 */
	public static UrlBuilder url( String... nodes ) {
		return new UrlBuilder( nodes );
	}
}
