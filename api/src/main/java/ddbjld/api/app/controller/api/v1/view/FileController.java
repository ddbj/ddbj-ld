package ddbjld.api.app.controller.api.v1.view;

import lombok.extern.slf4j.Slf4j;
import ddbjld.api.app.core.module.AuthModule;
import ddbjld.api.app.core.module.FileModule;
import ddbjld.api.data.beans.ProjectFileInfo;
import ddbjld.api.app.service.FileService;
import ddbjld.api.app.service.AuthService;
import ddbjld.api.common.annotation.Auth;
import ddbjld.api.common.exceptions.RestApiException;
import ddbjld.api.data.beans.UploadToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

/**
 * ファイル（NextCloud）に対するコントローラクラス.
 *
 * @author m.tsumura
 *
 **/
@RestController
@RequestMapping( path = {
		"v1/view/project/{project-id}/file",
		"view/project/{project-id}/file"
})
@Slf4j
public class FileController {

	@Autowired
	private AuthModule authModule;
	
	@Autowired
	private FileService fileService;

	@Autowired
	private AuthService authService;

	/**
	 * ファイル一覧取得API.
	 *
	 * <p>
	 * 特定のプロジェクトにアップロードされているファイルの一覧を取得する<br>
	 * 編集中の未公開プロジェクトの一覧を取得する場合は認証ヘッダが必要である。
	 * </p>
	 *
	 * @param projectId
	 * @return ファイル一覧
	 *
	 **/
	@GetMapping("list")
	public List<ProjectFileInfo> getProjectFile(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("project-id") final String projectId) {
		UUID accountUUID = authModule.getAuthAccountUUID(request);

		return null == accountUUID
				? this.fileService.getProjectFile(projectId)
				: this.fileService.getProjectFile(accountUUID, projectId);
	}

	/**
	 * ファイル登録・更新API.
	 *
	 * <p>
	 * ユーザが所属しているプロジェクトにファイルをアップロードする<br>
	 * </p>
	 *
	 * @param projectId
	 * @param fileType
	 * @param fileName
	 * @param token
	 * @param multipartFiles
	 *
	 * @return void
	 *
	 **/
	@PostMapping(value = "{file-type}/{file-name}/{token}", consumes = "multipart/form-data")
	public ProjectFileInfo upload(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("project-id") final String projectId,
			@PathVariable("file-type") final String fileType,
			@PathVariable("file-name") final String fileName,
			@PathVariable("token") final UUID token,
			@RequestParam("files") final MultipartFile[] multipartFiles) {
		byte[] file      = FileModule.Converter.toByte(multipartFiles[0]);

		if(null == file) {
			throw new RestApiException(HttpStatus.BAD_REQUEST);
		}

		return this.fileService.upload(projectId, fileType, fileName, file, token);
	}

	/**
	 * ファイルダウンロードAPI.
	 *
	 * <p>
	 * リクエストされたファイルをダウンロードする<br>
	 * </p>
	 *
	 * @param projectId
	 * @param fileType
	 * @param fileName
	 *
	 * @return ファイル
	 *
	 **/
	@GetMapping("{file-type}/{file-name}")
	public ResponseEntity<byte[]> downLoad(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("project-id") final String projectId,
			@PathVariable("file-type") final String fileType,
			@PathVariable("file-name") final String fileName) {
		
		// 公開プロジェクトはログイン認証なしでも参照可能、添付ファイルもダウンロード可能。
		UUID accountUUID = authModule.getAuthAccountUUID(request);

		byte[] file = null == accountUUID
				? this.fileService.download(projectId, fileType, fileName)
				: this.fileService.download(accountUUID, projectId, fileType, fileName);

		// 強制ダウンロードを示すヘッダをレスポンスに付与
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/force-download");
		headers.add("Content-Disposition", "attachment; filename*=utf-8''" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));

		return new ResponseEntity<byte[]>(file, headers, HttpStatus.OK);
	}

	/**
	 * ファイル削除API.
	 *
	 * <p>
	 * リクエストされたファイルを削除する<br>
	 * </p>
	 *
	 * @param projectId
	 * @param fileType
	 * @param fileName
	 *
	 * @return ファイル
	 *
	 **/
	@DeleteMapping("{file-type}/{file-name}")
	@Auth
	public void delete(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("project-id") final String projectId,
			@PathVariable("file-type") final String fileType,
			@PathVariable("file-name") final String fileName) {
		UUID accountUUID = authModule.getAuthAccountUUID(request);

		this.fileService.delete(accountUUID, projectId, fileType, fileName);
	}

	/**
	 * ファイルアップロードトークン発行API.
	 *
	 * <p>
	 * ファイルをアップロードするためのトークンを発行する<br>
	 * </p>
	 *
	 * @param projectId
	 * @param fileType
	 * @param fileName
	 *
	 * @return アップロードトークン
	 *
	 **/
	@PostMapping("{file-type}/{file-name}")
	@Auth
	public UploadToken preUpload(
			final HttpServletRequest request,
			final HttpServletResponse response,
			@PathVariable("project-id") final String projectId,
			@PathVariable("file-type") final String fileType,
			@PathVariable("file-name") final String fileName) {
		UUID accountUUID = authModule.getAuthAccountUUID(request);

		return this.fileService.preUpload(accountUUID, projectId, fileType, fileName);
	}
}
