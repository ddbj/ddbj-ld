package com.ddbj.ld.data.json.elasticsearch;

import java.util.HashMap;

public class ESQuery {
	
	public static interface IElasticSearchQuery { 
		// 同一視するためのマーカインタフェースなので実装は特に無い。
	}
	
	public static class TermQuery implements IElasticSearchQuery {
		public final HashMap<String, String> term;
		
		public TermQuery( String property, String value ) {
			this.term = new HashMap<>();
			this.term.put( property, value );
		}
	}
	
	public static class TermsQuery implements IElasticSearchQuery {
		public final HashMap<String, String[]> terms;
		
		public TermsQuery( String property, String[] values ) {
			this.terms = new HashMap<>();
			this.terms.put( property, values );
		}
	}
	
	public static class WildcardQuery implements IElasticSearchQuery {
		
		public final HashMap<String, String> wildcard;
		
		public WildcardQuery( String property, String value ) {
			this.wildcard = new HashMap<>();
			this.wildcard.put( property + ".keyword", "*" + value + "*" );
		}
	}
	
	public static class MatchQuery implements IElasticSearchQuery {
		
		public final HashMap<String, String> match;

		public MatchQuery( String property, String value ) {
			this.match = new HashMap<>();
			this.match.put( property, value );
		}
	}
}
