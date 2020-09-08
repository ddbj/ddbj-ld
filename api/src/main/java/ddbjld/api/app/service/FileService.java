package ddbjld.api.app.service;

import lombok.extern.slf4j.Slf4j;
import ddbjld.api.app.core.module.FileModule;
import ddbjld.api.data.beans.ProjectFileInfo;
import ddbjld.api.app.service.dao.FileDao;
import ddbjld.api.app.service.dao.UploadDao;
import ddbjld.api.app.service.dao.ProjectDao;
import ddbjld.api.app.service.dao.ProjectRoleDao;
import ddbjld.api.app.service.dao.UserDao;
import ddbjld.api.common.exceptions.RestApiException;
import ddbjld.api.common.utility.UrlBuilder;
import ddbjld.api.data.beans.UploadToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * ファイル管理機能のサービスクラス.
 *
 * @author m.tsumura
 *
 **/
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class FileService {

	private static final String DRAFT_FOLDER = "draft";
	
    @Autowired
    private FileModule fileModule;

    @Autowired
    private FileDao fileDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProjectRoleDao projectRoleDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private UploadDao uploadDao;

    /**
     * 公開されているプロジェクトの持つファイル一覧を取得するメソッド.
     *
     * <p>
     * t_fileを検索し、プロジェクトの持つファイルを取得する。<br>
     * その中からNextCloudの公開フォルダにファイルがあるか確認し、あるものだけプロジェクト一覧として返す
     * </p>
     *
     * @param projectId
     *
     * @return プロジェクト一覧
     *
     **/
    public List<ProjectFileInfo> getProjectFile(
            final String projectId) {
        var project     = new ProjectDao.TProjectDataReader(this.projectDao.read(projectId));
        var projectUuid = project.uuid();
        var published   = project.published();
        var hidden      = project.hidden();

        if(false == published || hidden) {
            throw new RestApiException(HttpStatus.BAD_REQUEST);
        }

        var records = this.fileDao.readByProjectUuid(projectUuid);
        var result = new ArrayList<ProjectFileInfo>();

        for(var record : records) {
            var file     = new FileDao.TFileDataReader(record);
            var type     = file.type();
            var name     = file.name();
            var nameForUrl = this.fileModule.getFileNameForNextCloud(name);
            var url      = fileModule.getFileUrl(projectId, type, nameForUrl);

            if(fileModule.exists(projectId, type, nameForUrl)) {
                var info = new ProjectFileInfo();
                info.setType(type);
                info.setName(name);
                info.setUrl(url);

                result.add(info);
            }
        }

        return result;
    }

    /**
     * 編集中の未公開プロジェクトの持つファイル一覧を取得するメソッド.
     *
     * <p>
     * 使用には対象のプロジェクトに対し、アカウントが読み取り権限が必要である。<br>
     * t_fileを検索し、プロジェクトの持つファイルを取得する。<br>
     * その中からNextCloudのdraftフォルダにファイルがあるか確認し、あるものだけプロジェクト一覧として返す
     * </p>
     *
     * @param projectId
     *
     * @return プロジェクト一覧
     *
     **/
    public List<ProjectFileInfo> getProjectFile(
            final UUID accountUuid,
            final String projectId
    ) {
        var project     = new ProjectDao.TProjectDataReader(this.projectDao.read(projectId));
        var projectUuid = project.uuid();
        // FIXME もっと簡易な条件
        var readable    = this.projectRoleDao.hasOwner(accountUuid, projectUuid) || this.projectRoleDao.hasWritable(accountUuid, projectUuid) || this.projectRoleDao.hasReadable(accountUuid, projectUuid);
        var editing     = project.editing();

        if(false == readable || false == editing
        ) {
            // アカウントに読み込み権限がない、もしくはプロジェクトが編集中でない場合はアップロードさせない
            throw new RestApiException(HttpStatus.BAD_REQUEST);
        }

        var records = this.fileDao.readByProjectUuid(projectUuid);
        var result = new ArrayList<ProjectFileInfo>();

        for(var record : records) {
            var file     = new FileDao.TFileDataReader(record);
            var type     = file.type();
            var name     = file.name();
            var url      = this.fileModule.getFileUrl(projectId, type, name);

            var nameForUrl = this.fileModule.getFileNameForNextCloud(name);

            if(fileModule.exists(projectId, DRAFT_FOLDER, type, nameForUrl)) {
                var info = new ProjectFileInfo();
                info.setType(type);
                info.setName(name);
                info.setUrl(url);

                result.add(info);
            }
        }

        return result;
    }

    /**
     * ファイルをアップロード（1ファイル）するメソッド.
     *
     * <p>
     * 使用には対象のプロジェクトに対しアカウントが書き込み権限、プロジェクトが編集中であることが必要である。<br>
     * NextCloudのdraftフォルダにファイルをアップロードする。
     * </p>
     *
     * @param projectId
     * @param type
     * @param name
     * @param file
     * @param token
     *
     * @return ファイル情報
     *
     **/
    public ProjectFileInfo upload(
            final String projectId,
            final String type,
            final String name,
            final byte[] file,
            final UUID token
            ) {

        var reader = new UploadDao.TUploadDataReader(this.uploadDao.read(token));

        if(null == reader.data) {
            throw new RestApiException(HttpStatus.BAD_REQUEST);
        }

        var accountUuid = reader.accountUuid();

        var project     = new ProjectDao.TProjectDataReader(this.projectDao.read(projectId));
        var projectUuid = project.uuid();
        var writable    = this.projectRoleDao.hasWritable(accountUuid, projectUuid);
        var editing     = project.editing();
        var hidden      = project.hidden();

        if(false == writable || false == editing || hidden
        ) {
            // アカウントに書き込み権限がない、もしくはプロジェクトが編集中でない、非公開状態の場合はアップロードさせない
            throw new RestApiException(HttpStatus.BAD_REQUEST);
        }

        if(false == this.fileDao.exists(projectUuid, type, name)) {
            this.fileDao.create(projectUuid, type, name);
        }

        // NextCloudのフォルダ作成APIは再帰的に作成できないため、存在しない場合1階層ずつ作っていく
        if(false == this.fileModule.exists(projectId)) {
            this.fileModule.createDirectory(projectId);
        }

        if(false == this.fileModule.exists(projectId, DRAFT_FOLDER)) {
            this.fileModule.createDirectory(projectId, DRAFT_FOLDER);
        }

        if (false == this.fileModule.exists(projectId, DRAFT_FOLDER, type)) {
            this.fileModule.createDirectory(projectId, DRAFT_FOLDER, type);
        }

        // NextCloudに登録する様にスペースを変換する
        var nameForUrl = name
                .replaceAll("\\s", "%20")
                .replace("　", "%E3%80%80");

        // ファイルをアップロード
        if(false == this.fileModule.upload(file, projectId, DRAFT_FOLDER, type, nameForUrl)) {
            throw new RestApiException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // アップロードトークンを削除
        this.uploadDao.delete(token);

        var projectFileInfo = new ProjectFileInfo();

        var url = fileModule.getFileUrl(projectId, type, name);

        projectFileInfo.setType(type);
        projectFileInfo.setName(name);
        projectFileInfo.setUrl(url);

        return projectFileInfo;
    }

    /**
     * 公開されているプロジェクトのファイルをダウンロード（1ファイル）するメソッド.
     *
     * <p>
     * NextCloudの公開フォルダのファイルをダウンロードする。
     * </p>
     *
     * @param projectId
     * @param type
     * @param name
     *
     * @return ファイル
     *
     **/
    public byte[] download(
            final String projectId,
            final String type,
            final String name
    ) {
        var project   = new ProjectDao.TProjectDataReader(this.projectDao.read(projectId));
        var published = project.published();
        var hidden    = project.hidden();

        if(false == published || hidden) {
            throw new RestApiException(HttpStatus.BAD_REQUEST);
        }

        var nameForUrl = this.fileModule.getFileNameForNextCloud(name);

        var result = fileModule.download(projectId, type, nameForUrl);

        if (null == result) {
            throw new RestApiException(HttpStatus.BAD_REQUEST);
        }

        return result;
    }

    /**
     * 編集中の未公開のプロジェクトのファイルをダウンロード（1ファイル）するメソッド.
     *
     * <p>
     * 使用には対象のプロジェクトに対しアカウントが読み込み権限、プロジェクトが編集中であることが必要である。<br>
     * NextCloudの公開フォルダのファイルをダウンロードする。
     * </p>
     *
     * @param accountUuid
     * @param projectId
     * @param type
     * @param name
     *
     * @return ファイル
     *
     **/
    public byte[] download(
            final UUID accountUuid,
            final String projectId,
            final String type,
            final String name
    ) {
        var project     = new ProjectDao.TProjectDataReader(this.projectDao.read(projectId));
        var projectUuid = project.uuid();
        var readable    = this.projectRoleDao.hasWritable(accountUuid, projectUuid);

        if(false == readable) {
            throw new RestApiException(HttpStatus.BAD_REQUEST);
        }

        var nameForUrl = this.fileModule.getFileNameForNextCloud(name);

        var result = fileModule.download(projectId, DRAFT_FOLDER, type, nameForUrl);

        if (null == result) {
            throw new RestApiException(HttpStatus.BAD_REQUEST);
        }

        return result;
    }

    /**
     * 編集中の未公開プロジェクトの持つファイルを削除するメソッド.
     *
     * <p>
     * 使用には対象のプロジェクトに対しアカウントが書き込み権限、プロジェクトが編集中であることが必要である。<br>
     * NextCloudのdraftフォルダに格納されている対象のファイルを1つ削除する。
     * </p>
     *
     * @param projectId
     * @param type
     * @param name
     *
     * @return void
     *
     **/
    public void delete(
            final UUID accountUuid,
            final String projectId,
            final String type,
            final String name
    ) {
        var project     = new ProjectDao.TProjectDataReader(this.projectDao.read(projectId));
        var projectUuid = project.uuid();

        var writable    = this.projectRoleDao.hasWritable(accountUuid, projectUuid);
        var editing     = project.editing();
        var hidden      = project.hidden();

        if(false == writable || false == editing || hidden
        ) {
            // アカウントに書き込み権限がない、もしくはプロジェクトが編集中でない、非公開状態の場合はアップロードさせない
            throw new RestApiException(HttpStatus.BAD_REQUEST);
        }

        var nameForUrl = this.fileModule.getFileNameForNextCloud(name);

        if(false == this.fileModule.delete(projectId, DRAFT_FOLDER, type, nameForUrl)) {
            throw new RestApiException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(false == this.fileModule.exists(projectId, type, nameForUrl)) {
            // 公開されている同名のファイルが存在しない場合のみ、削除する

            var reader = new FileDao.TFileDataReader(this.fileDao.readStrict(projectUuid, type, name));
            var uuid   = reader.uuid();

            this.fileDao.delete(uuid);
        }
    }

    /**
     * 編集中だったファイルをdraftフォルダからコピーし公開された状態にするメソッド.
     *
     * <p>
     * 申請依頼を出していたプロジェクトの査定が承認され、未公開→公開になったときに呼び出される。<br>
     * </p>
     *
     * @param accountUuid
     * @param projectId
     *
     * @return void
     *
     **/
    public void publish(
            final UUID accountUuid,
            final String projectId
	) {
		// TODO：他Serviceから呼び出すとAdminチェックがダブっちゃうので、やり方を後で考える。
    	// 承認処理はDDBJAdminユーザにしかさせない。
    	this.userDao.validateAdminableUser(accountUuid);
    	
    	
        var project     = new ProjectDao.TProjectDataReader(this.projectDao.read(projectId));
        var projectUuid = project.uuid();
        var editing     = project.editing();
        var hidden      = project.hidden();

        if(editing || hidden) {
			// TODO：この条件必要かどうか後で確認。
            // プロジェクトが編集完了していない、もしくは非公開状態なら拒否
            throw new RestApiException(HttpStatus.BAD_REQUEST);
        }

        var records = this.fileDao.readByProjectUuid(projectUuid);

        for(var record : records) {
            var file     = new FileDao.TFileDataReader(record);
            var type = file.type();
            var name = file.name();

            var nameForUrl = this.fileModule.getFileNameForNextCloud(name);

            // NextCloudのdraftフォルダから公開フォルダにコピーする
            var draftPath      = UrlBuilder.url(projectId, DRAFT_FOLDER, type, nameForUrl).build();
            var path           = UrlBuilder.url(projectId, type, nameForUrl).build();

            if(fileModule.exists(path)
             && false == fileModule.exists(draftPath)) {
                // 編集開始前に存在したが、編集の結果削除されたファイルは削除
                this.fileModule.delete(path);
                var target = new FileDao.TFileDataReader(this.fileDao.readStrict(projectUuid, type, name));
                var uuid   = target.uuid();

                this.fileDao.delete(uuid);

                continue;
            }

            if(false == this.fileModule.exists(projectId, type)) {
                this.fileModule.createDirectory(projectId, type);
            }

            this.fileModule.copy(draftPath, path);
        }

        // draftフォルダは不要になったため削除
        this.fileModule.delete(projectId, DRAFT_FOLDER);
    }

    /**
     * 編集途中で編集が破棄されたときに呼び出されるメソッド.
     *
     * <p>
     * 編集が破棄されたときに呼び出される<br>
     * </p>
     *
     * @param accountUuid
     * @param projectId
     *
     * @return void
     *
     **/
    public void discard(
            final UUID accountUuid,
            final String projectId
    ) {
		// TODO：他Serviceから呼び出すとAdminチェックがダブっちゃうので、やり方を後で考える。
    	// 否認処理はDDBJAdminユーザにしかさせない。
    	this.userDao.validateAdminableUser(accountUuid);
    	
    	
        var project     = new ProjectDao.TProjectDataReader(this.projectDao.read(projectId));
        var projectUuid = project.uuid();
        var editing     = project.editing();
        var hidden      = project.hidden();

        if(editing || hidden) {
			// TODO：この条件必要かどうか後で確認。
            // プロジェクトが編集完了していない、もしくは非公開状態なら拒否
            throw new RestApiException(HttpStatus.BAD_REQUEST);
        }

        var records = this.fileDao.readByProjectUuid(projectUuid);

        for(var record : records) {
            var file     = new FileDao.TFileDataReader(record);
            var type = file.type();
            var name = file.name();

            var nameForUrl = this.fileModule.getFileNameForNextCloud(name);

            // draftフォルダのみあるファイルのレコードを削除する
            var draftPath      = UrlBuilder.url(projectId, type, DRAFT_FOLDER, nameForUrl).build();
            var path           = UrlBuilder.url(projectId, type, nameForUrl).build();

            if(fileModule.exists(draftPath)
                    && false == fileModule.exists(path)) {
                // 編集開始後の新規でアップロードされたファイルのレコードを削除
                var target = new FileDao.TFileDataReader(this.fileDao.readStrict(projectUuid, type, name));
                var uuid   = target.uuid();

                this.fileDao.delete(uuid);

                continue;
            }
        }

        // draftフォルダは不要になったため削除
        this.fileModule.delete(projectId, DRAFT_FOLDER);
    }

    /**
     * アップロードをする際に必要なワンタイムトークンを発行するメソッド.
     *
     * <p>
     * アップロードの前処理として呼び出される<br>
     * </p>
     *
     * @param accountUuid
     * @param projectId
     * @param fileType
     * @param fileName
     *
     * @return void
     *
     **/
    public UploadToken preUpload(
            final UUID accountUuid,
            final String projectId,
            final String fileType,
            final String fileName
            ) {
        var project = new ProjectDao.TProjectDataReader(this.projectDao.read(projectId));

        if(null == project.data) {
            throw  new RestApiException(HttpStatus.BAD_REQUEST);
        }

        // FIXME 後でコードを統一
        var projectUuid = project.uuid();

        var writable    = this.projectRoleDao.hasWritable(accountUuid, projectUuid);
        var editing     = project.editing();
        var hidden      = project.hidden();

        if(false == writable || false == editing || hidden
        ) {
            // アカウントに書き込み権限がない、もしくはプロジェクトが編集中でない、非公開状態の場合はアップロードさせない
            throw new RestApiException(HttpStatus.BAD_REQUEST);
        }

        if(this.uploadDao.isUploading(accountUuid, projectUuid, fileType, fileName)) {
            // 既に他のユーザーがアップロード中であるなら、競合エラー
            throw new RestApiException(HttpStatus.CONFLICT);
        }

        UUID token = this.uploadDao.create(accountUuid, projectUuid, fileType, fileName);

        var uploadToken = new UploadToken();
        uploadToken.setUploadToken(token);

        return uploadToken;
    }
}
