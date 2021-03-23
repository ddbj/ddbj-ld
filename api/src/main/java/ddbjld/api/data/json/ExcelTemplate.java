package ddbjld.api.data.json;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import ddbjld.api.common.utility.JsonMapper;
import ddbjld.api.common.utility.StringUtil;

@Deprecated // FIXME：結局サーバサイドロジックではあまり使わなくなったので、定義ファイルごと消しても良いかも。
//template.json で定義する、Excel入力データスキーマのJSONマッピングクラス。
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExcelTemplate {

	// FIXME：プログラム中で値を書き換えることはないので、全フィールド final にしたい。（後回し）
	
	public enum LabelType {
		// quicktype.io 自動生成
	    DATE, DOI, FLOAT, ID, STRING, TEXT, URI, URL, YEAR;

	    @JsonValue
	    public String toValue() {
	        switch (this) {
	            case DATE: return "date";
	            case DOI: return "doi";
	            case FLOAT: return "float";
	            case ID: return "id";
	            case STRING: return "string";
	            case TEXT: return "text";
	            case URI: return "uri";
	            case URL: return "url";
	            case YEAR: return "year";
	        }
	        return null;
	    }

	    @JsonCreator
	    public static LabelType forValue(String value) throws IOException {
			if ( value.equals( "date" ) ) return DATE;
			if ( value.equals( "doi" ) ) return DOI;
			if ( value.equals( "float" ) ) return FLOAT;
			if ( value.equals( "id" ) ) return ID;
			if ( value.equals( "string" ) ) return STRING;
			if ( value.equals( "text" ) ) return TEXT;
			if ( value.equals( "uri" ) ) return URI;
			if ( value.equals( "url" ) ) return URL;
			if ( value.equals( "year" ) ) return YEAR;
		    throw new IOException("Cannot deserialize LabelType");
	    }
	}
	
	public enum Required {
		// quicktype.io 自動生成
		ALT_1, DDBJ, NO, YES;
		
		//TODO：Yes/No 以外は用途が良く解らんので要確認。
		
		@JsonValue
		public String toValue() {
			switch ( this ) {
				case ALT_1:
					return "alt.1";
				case DDBJ:
					return "ddbj";
				case NO:
					return "no";
				case YES:
					return "yes";
			}
			return null;
		}
		
		@JsonCreator
		public static Required forValue( String value ) throws IOException {
			if ( value.equals( "alt.1" ) ) return ALT_1;
			if ( value.equals( "ddbj" ) ) return DDBJ;
			if ( value.equals( "no" ) ) return NO;
			if ( value.equals( "yes" ) ) return YES;
			throw new IOException( "Cannot deserialize Required" );
		}
	}
	
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Content {

		public String label_id;
	    @Deprecated
		public String label_name;
	    @JsonProperty("label_name.ja")
	    public String label_name_ja;
	    //public LabelType label_type;
	    //public String rdf_subject_prefix;
	    //public String rdf_predicate;
	    //public String rdf_object_data_type;
	    //public String rdf_object_prefix;
	    public Filter filter; // filter.select に別オブジェクトへの参照定義がある。
	    //public String auto_fill;
	    //public String locale;
	    //public String help;
		//public String help_ja;
	    
	    public String mb_show;
	    public String mb_go_refer;
		
	    public boolean hasRelation() {
	    	return null != this.filter
    			&& null != this.filter.select
    			&& StringUtil.notNullOrEmpty( this.filter.select.target_sheet_id )
    			&& StringUtil.notNullOrEmpty( this.filter.select.target_label_id );
	    }
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Filter {
	    public Required required;
	    public Select select;
	
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Select {
	    //public String type = "refer";
	    //public String add;
		
//	    public String target_sheet;
//	    public String target_label;
	    public String target_sheet_id;
	    public String target_label_id;
	
	}


    //public String dataType = "template";
    public String sheet_id;
	@Deprecated
    public String sheet_name;
    @JsonProperty("sheet_name.ja")
    public String sheet_name_ja;
    //public String sheet_desc;
    //public String sheet_desc_ja;
    public String mb_menu_show;
    public Content[] contents;
    
    
    public static ExcelTemplate parse(final String json ) {
    	return JsonMapper.parse( json, ExcelTemplate.class );
    }
    
	@Deprecated // FIXME：要否を確認しつつenumにしてちゃんと実装する。
    public static class MBMenuShow {
    	
		public static String of( String value ) {
			if ( null == value ) return "[自動判定]";
			
			switch ( value ) {
				case "yes":
					return "[メニュー表示]";
				case "no":
					return "[メニュー非表示]";
				default:
					return value;
			}
    	}
    }

	@Deprecated // FIXME：要否を確認しつつenumにしてちゃんと実装する。
    public static class MBShow {

    	public static String of( String value ) {
			if ( null == value ) return "[自動判定]";
			
			switch ( value ) {
				case "yes":
					return "[表示]";
				case "no":
					return "[非表示]";
				default:
					return value;
			}
		}
    }
}
