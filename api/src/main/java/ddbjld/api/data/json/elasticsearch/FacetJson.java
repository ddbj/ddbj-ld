package ddbjld.api.data.json.elasticsearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import ddbjld.api.common.utility.JsonMapper;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacetJson {
	// 「検索画面：個別検索」用に抽出してきたデータ
	
	/** 生物種名 */
	public String[] species;
	
	/** 分析機器 */
	public String[] devices;

	FacetJson() {
		this( null, null );
	}
	public FacetJson( String[] species, String[] devices ) {
		this.species = species;
		this.devices = devices;
	}

	// json
	// ※ これ必要ないけど他のJSON系クラスと実装対称性確保する意味で一通り実装しとく。
	
	public String stringify() {
		return JsonMapper.stringify( this );
	}
	
	public static FacetJson parse( final String json  ) {
		return null == json ? null : JsonMapper.parse( json, FacetJson.class );
	} 
}
