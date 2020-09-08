package ddbjld.api.app.feasibility.controller;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ddbjld.api.app.config.TemplateJson;
import ddbjld.api.common.annotation.Auth;
import ddbjld.api.common.utility.JsonMapper;
import ddbjld.api.data.json.ExcelSheetData;
import ddbjld.api.data.json.ExcelTemplate;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Collectors;

// DDBJで使用する機能のフィジビリティを取るためのコントローラ
@Deprecated
@RequestMapping("ddbj/feasibility/api")
@RestController
@AllArgsConstructor
@Slf4j
public class FeasibilityApiController {
	private MailSender mailSender;

	@GetMapping("auth")
	@Auth
	public String auth() {
		return "OK";
	}

	@GetMapping("path/{projectId}/{accountId}")
	public String path(
			@PathVariable("projectId") String projectId,
			@PathVariable("accountId") String accountId
	) {
		return projectId + ":" + accountId;
	}

	@PostMapping("mail")
	public String mail() {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("test@mail.com");
		mail.setTo("m.tsumura@denno.biz");
		mail.setSubject("テストメール");
		mail.setText( MailTemplate.process( "Spring Boot より本文送信" ) );

		try {
			this.mailSender.send(mail);
		} catch (MailException e) {
			return "NG:" + e.getMessage();
		}

		return "OK";
	}
	
	@RequestMapping("mail/{msg}")
	public String mail(@PathVariable String msg) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("test@mail.com");
//		mail.setFrom( "ryo.sugawara@gmail.com" );
		mail.setTo( "ryo.sugawara@gmail.com" );
		mail.setSubject( "テストメール" );
		mail.setText( MailTemplate.process( msg ) );
		
		try {
			this.mailSender.send(mail);
		} catch (MailException e) {
			return "NG:" + e.getMessage();
		}

		return "OK:" + msg;
	}

	private static class MailTemplate {
		
		//FIXME：後でちゃんと部品化。
		
		private static String process( String msg ) {
			
			// TODO：resolverは都度生成しない方が良さそう、調べてからシングルトンに変更。（部品化とあわせて対応する）
			var resolver = new ClassLoaderTemplateResolver();
			resolver.setTemplateMode( TemplateMode.TEXT );
			resolver.setPrefix( "templates/mail/" ); // src/mail/resources/templates/mail
			resolver.setSuffix( ".mt" );
			resolver.setCharacterEncoding( "UTF-8" );
			resolver.setCacheable( true );
			
			var template = new SpringTemplateEngine();
			template.setTemplateResolver( resolver );
			
			
			// TODO：↑たぶんここまでキャッシュ可能。
			
			// TODO：↓都度生成はここからで十分。
			
			
			Context context = new Context();
			context.setVariable( "msg", msg );
			
			return template.process( "testmail", context );
		}
	}
	
	@GetMapping("test/template-json")
	public ExcelTemplate[] template() {

		ExcelTemplate[] schema = TemplateJson.schema();
		
		log.debug( JsonMapper.stringify( schema ) );
		return schema;
	}
	
	@GetMapping("test/sample-data")
	public ExcelSheetData[] sampledata() {

		ExcelSheetData[] data = SampleData.load();

		log.debug( JsonMapper.stringify( data ) );
		return data;
	}
	@Deprecated
	private static class SampleData {
		
		// FIXME：実装フェーズで使うサンプルデータの読み込み。リリース時にはconfig/jsonファイルごと消す。
		
		public static ExcelSheetData[] load() {
			return JsonMapper.parse( sample(), ExcelSheetData[].class );
		}
		
		private static String sample() {
			try {
				// src/main/resources/config/template.json を読み込み
				File file = ResourceUtils.getFile( "classpath:config/sampledata.json" );
				return Files
						.lines( file.toPath(), StandardCharsets.UTF_8 )
						.collect( Collectors.joining( "" ) );
			} catch ( IOException ex ) {
				throw new RuntimeException( ex );
			}
		}
	}
	

	@PostMapping("project/{projectId}")
	public String project(@PathVariable String projectId) {
		// TODO Util化
		Sardine sardine = SardineFactory.begin("root", "root");
		String url = "http://localhost:8083/remote.php/dav/files/root/" + projectId;

		try  {
			sardine.createDirectory(url);

			if(sardine.exists(url)) {
				return "OK:" + projectId;
			}

			return "NG:Not found";
		} catch (IOException e) {
			return "NG:" + e.getMessage();
		}
	}

	@PostMapping("project/{projectId}/{metadataId}/{fileName}")
	public String upload(
			@PathVariable("projectId") String projectId,
			@PathVariable("metadataId") String metadataId,
			@PathVariable("fileName") String fileName,
			// TODO 手を抜かないでちゃんと他の項目と一緒にJson形式にしたい
			@RequestBody String file
	) {
		// TODO Util化
		Sardine sardine = SardineFactory.begin("root", "root");
		byte[] byteFile = file.getBytes();

		try {
			String baseUrl = "http://localhost:8083/remote.php/dav/files/root/";
			String projectUrl = baseUrl + projectId + "/";
			String metaDataUrl = projectUrl + metadataId+ "/";
			String fileUrl = metaDataUrl + fileName;

			// TODO 存在チェックのUtilがほしい
			if(! sardine.exists(projectUrl)) {
				sardine.createDirectory(projectUrl);
			}

			if(! sardine.exists(metaDataUrl)) {
				sardine.createDirectory(metaDataUrl);
			}

			sardine.put(fileUrl, byteFile);

			if(! sardine.exists(fileUrl)) {
				return "NG:Not Found";
			}
		} catch (IOException e) {
			return "NG:" + e.getMessage();
		}

		return "OK:" + projectId;
	}

	@GetMapping("project/{projectId}/{metadataId}/{fileName}")
	public ResponseEntity<byte[]> download(
			@PathVariable("projectId") String projectId,
			@PathVariable("metadataId") String metadataId,
			@PathVariable("fileName") String fileName
	) {
		// TODO Util化
		Sardine sardine = SardineFactory.begin("root", "root");
		String url = "http://localhost:8083/remote.php/dav/files/root/" + projectId + "/" + metadataId + "/" + fileName;

		try(InputStream inputStream = sardine.get(url);) {
			byte[] file = IOUtils.toByteArray(inputStream);
			HttpHeaders headers = new HttpHeaders();

			// 強制ダウンロードを示すヘッダ。
			headers.add("Content-Type", "application/force-download");
			// TODO Excelファイルとかだったらどうなるの？
			// 強制ダウンロードを示すヘッダ。
			headers.add("Content-Disposition", "attachment; filename*=utf-8''" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));

			return new ResponseEntity<byte[]>(file, headers, HttpStatus.OK);
		} catch (IOException e) {
			return null;
		}
	}

	@DeleteMapping("project/{projectId}/{metadataId}/{fileName}")
	public String delete(
			@PathVariable("projectId") String projectId,
			@PathVariable("metadataId") String metadataId,
			@PathVariable("fileName") String fileName
	) {
		// TODO Util化
		Sardine sardine = SardineFactory.begin("root", "root");
		String url = "http://localhost:8083/remote.php/dav/files/root/" + projectId + "/" + metadataId + "/" + fileName;

		try {
			sardine.delete(url);
		} catch (IOException e) {
			return "NG:" + e.getMessage();
		}

		return "OK";
	}

}
