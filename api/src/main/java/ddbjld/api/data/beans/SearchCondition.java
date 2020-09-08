package ddbjld.api.data.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import ddbjld.api.common.utility.JsonMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SearchCondition {
	
	public enum OperationType {
		AND("must"), OR("should");
		
		public final String boolQueryType;
		
		private OperationType( String boolQueryType ) {
			this.boolQueryType = boolQueryType;
		}
		
		public static OperationType of( final String operation ) {
			switch ( operation.toUpperCase() ) {
				case "AND":
					return AND;
				case "OR":
					return OR;
				default:
					return null;
			}
		}
	}
	
	
	// 何れか必須;
	
	/** キーワード検索 */
	private String[] keyword;
	/** 個別検索：生物種名 */
	private String[] samples;
	/** 個別検索：分析機器の種別 */
	private String[] instruments;
	
	// 必須;
	
	/** 複合条件 {@code "AND" / "OR"} */
	private String operation; 
	
	// 任意;
	
	/** ページング：ページ番号（0-based-index） */
	private long offset; // page-index; ES.from
	/** ページング：ページサイズ */
	private long count;  // page-size; ES.size
	
	public static SearchCondition parse( final String json ) {
		return null == json ? null : JsonMapper.parse( json, SearchCondition.class );
	}
}
