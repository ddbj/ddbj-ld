package ddbjld.api.app.controller.v1.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//TODO：Project側からもメタデータの参照パスがあるが、メタデータを軸にしたパスも用意している。（最悪こっちは無くても良い）

@RestController
@RequestMapping(path = {"v1/view/metadata", "view/metadata"})
public class MetadataController {
	
	private static final Logger log = LoggerFactory.getLogger( MetadataController.class );
	
	/** ファイルのダウンロード */
	@GetMapping("/{ddbj_id_path}/file")
	@Deprecated
	Object pathProjectFile(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("ddbj_id_path") String mbidpath ) {
		
		//FIXME：id_pathに関しては要検討。
		
		return "";
	}
	
	/** メタデータ取得 */
	@GetMapping("/{ddbj_id_path}")
	@Deprecated
	Object pathMetadata(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("ddbj_id_path") String mbidpath ) {

		//FIXME：id_pathに関しては要検討。
		
		return "";
	}
	
	
}
