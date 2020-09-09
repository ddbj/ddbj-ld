package ddbjld.api.app.config;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import ddbjld.api.common.utility.JsonMapper;
import ddbjld.api.data.json.ExcelTemplate;


/**
 * {@code config/template.json} のJSON定義情報を持つシングルトンホルダ。
 * @author sugaryo
 * 
 * @see ResourceUtils
 * @see JsonMapper
 */
public class TemplateJson {

	private static final Logger log = LoggerFactory.getLogger( TemplateJson.class );
	
	public static ExcelTemplate[] schema() {
		return SingletonHolder.schemas;
	}

	/** Initialization-on-demand holder idiom */
	private static class SingletonHolder {
		private static final ExcelTemplate[] schemas = JsonMapper.parse( load(), ExcelTemplate[].class );
		
		//FIXME：sheet_idをキーに探索することが多いのでMapを先に構築した方が良いか？要検討。
		
		private static String load() {
			
			log.info( "★ config/template.json 読み込み★" );
			
			try {
				
				//TODO：この辺の処理はUtilityにしたいが後回し。
				
				// src/main/resources/config/template.json を読み込み
				File file = ResourceUtils.getFile( "classpath:config/template.json" );
				return Files
						.lines( file.toPath(), StandardCharsets.UTF_8 )
						.collect( Collectors.joining( "" ) );
			} catch ( IOException ex ) {
				throw new RuntimeException( ex );
			}
		}
	}
}
