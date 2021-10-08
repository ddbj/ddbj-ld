package com.ddbj.ld.data.json.elasticsearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.ddbj.ld.common.utility.JsonMapper;

// ElasticSearch index のデータをGETすると、全てこの形の入れ物でラップして返される。
// POST/PUT 時に送ったデータは json._source の中に入っている。
@JsonIgnoreProperties(ignoreUnknown = true)
public class ESIndexDataContainer<TSourceData> {
	
	// 本来は final 賭けたほうが安全だけど一時的なparseでしか使用しないし、値を書き換えても意味がないので一旦このままで。
	
	@JsonProperty("_index")
	public String index;
	@JsonProperty("_type")
	public String type;
	@JsonProperty("_id")
	public String id;
	@JsonProperty("_version")
	public long version;
	@JsonProperty("_seq_no")
	public long seq_no;
	@JsonProperty("_primary_term")
	public long primary_term;
	public boolean found;
	@JsonProperty("_source")
	public TSourceData source;
	
	public static ESIndexDataContainer<ESProjectIndex> parseProject( final String json ) {
		return JsonMapper.parse( json, 
				new TypeReference<ESIndexDataContainer<ESProjectIndex>>() {
		} );
	}
}
