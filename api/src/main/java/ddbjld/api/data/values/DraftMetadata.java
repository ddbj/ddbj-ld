package ddbjld.api.data.values;

import ddbjld.api.common.utility.JsonMapper;
import ddbjld.api.data.json.ExcelSheetData;

// client ExcelTool とやりとりする直接のデータ形式。
public class DraftMetadata {
	
	/** t_draft_metadata.metadata_json メタデータJSON */
	public final ExcelSheetData[] metadata;
	
	/** t_draft_metadata.data_token 更新トークン */
	public final String token;
	
	DraftMetadata() {
		// jackson用のデフォコン
		this( null, null );
	}
	public DraftMetadata( ExcelSheetData[] metadata, String token ) {
		this.metadata = metadata;
		this.token = token;
	}
	
	public static DraftMetadata parse( final String json ) {
		return null == json ? null : JsonMapper.parse( json, DraftMetadata.class );
	}
}
