package com.ddbj.ld.data.json;

import com.ddbj.ld.common.utility.JsonMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// t_project.metadata_json
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetadataJson {
	
	public final ExcelSheetData[] sheets;
	
	public MetadataJson() {
		this.sheets = new ExcelSheetData[0];
	}
	public MetadataJson( ExcelSheetData[] sheets ) {
		this.sheets = sheets;
	}

	public String stringify() {
		return JsonMapper.stringify( this );
	}
	
	public static MetadataJson parse( final String json ) {
		return null == json ? null : JsonMapper.parse( json, MetadataJson.class );
	}
}
