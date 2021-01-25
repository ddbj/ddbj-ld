package ddbjld.api.app.transact.service;

import ddbjld.api.app.config.ConfigSet;
import ddbjld.api.app.core.module.FileModule;
import ddbjld.api.app.core.module.ValidationModule;
import ddbjld.api.app.transact.dao.*;
import ddbjld.api.common.utility.UrlBuilder;
import ddbjld.api.data.model.v1.entry.jvar.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * エントリー機能のサービスクラス.
 *
 * @author m.tsumura
 *
 **/
@Service
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
@Slf4j
public class EntryService {

    private EntryDao entryDao;

    private HEntryDao hEntryDao;

    private EntryRoleDao entryRoleDao;

    private AccountDao accountDao;

    private UserDao userDao;

    private FileDao fileDao;

    private HFileDao hFileDao;

    private CommentDao commentDao;

    private UploadDao uploadDao;

    private FileModule fileModule;

    private ValidationModule validationModule;

    private ConfigSet config;

    public EntryResponse createEntry(final UUID accountUUID, final String type) {
        var entryUUID = this.entryDao.create(type);
        this.hEntryDao.insert(
                entryUUID,
                null,
                type,
                // FIXME 設定値を外だし
                "Unsubmitted",
                "Unvalidated",
                null,
                null,
                true,
                null,
                null
        );

        this.entryRoleDao.insert(accountUUID, entryUUID);

        var response = new EntryResponse();
        response.setUuid(entryUUID);
        // FIXME 外だし
        response.setStatus("Unsubmitted");
        // FIXME 外だし
        response.setValidationStatus("Unvalidated");

        var entry = this.entryDao.read(entryUUID);

        response.setUpdateToken(entry.getUpdateToken());

        return response;
    }

    public void deleteComment(
            final UUID commentUUID
    ) {
        this.commentDao.delete(commentUUID);
    }

    public EntriesResponse getEntries(
            final UUID accountUUID
    ) {
        var roles = this.entryRoleDao.readByAccountUUID(accountUUID);

        var response = new EntriesResponse();

        for(var role : roles) {
            var entryUUID   = role.getEntryUUID();

            // 論理削除されているエントリーは対象外
            var record      = this.entryDao.read(entryUUID);

            if(null == record) {
                continue;
            }

            var entry = new EntryResponse();

            entry.setUuid(entryUUID);
            entry.setLabel(record.getLabel());
            entry.setType(record.getType());
            entry.setValidationStatus(record.getValidationStatus());
            entry.setStatus(record.getStatus());
            entry.setIsDeletable(this.isDeletable(accountUUID, entryUUID));

            response.add(entry);
        }

        // label順でソートする
        Collections.sort(response, new Comparator<EntryResponse>(){
            public int compare(EntryResponse a1, EntryResponse a2) {
                return a1.getLabel().compareTo(a2.getLabel());
            }
        });

        return response;
    }

    // 管理者用全エントリー取得メソッド
    public EntriesResponse getAllEntries(
            final UUID accountUUID
    ) {
        var response = new EntriesResponse();
        // アカウントが管理者の場合は論理削除されてる以外の全てのエントリーを取得する
        var records = this.entryDao.all();

        for(var record: records) {
            var entry = new EntryResponse();
            var uuid  = record.getUuid();

            entry.setUuid(uuid);
            entry.setLabel(record.getLabel());
            entry.setType(record.getType());
            entry.setStatus(record.getStatus());
            entry.setValidationStatus(record.getValidationStatus());
            entry.setIsDeletable(this.isDeletable(accountUUID, uuid));

            response.add(entry);
        }

        return response;
    }

    public boolean hasRole(
            final UUID accountUUID,
            final UUID entryUUID
    ) {
        var hasRole = this.entryRoleDao.hasRole(accountUUID, entryUUID);
        var isAdmin = this.userDao.isAdmin(accountUUID);

        return hasRole || isAdmin;
    }

    public boolean isDeletable(
            final UUID accountUUID,
            final UUID entryUUID
    ) {
        return (this.entryRoleDao.hasRole(accountUUID, entryUUID)
             || this.userDao.isAdmin(accountUUID))
             && false == this.hEntryDao.isPublishedOnce(entryUUID);
    }

    public boolean isDeletablePhysically(
            final UUID entryUUID
    ) {
        return this.hEntryDao.countByUUID(entryUUID) == 1;
    }

    public boolean isSubmittable(
            final UUID accountUUID,
            final UUID entryUUID
    ) {
        return (this.entryRoleDao.hasRole(accountUUID, entryUUID)
                || this.userDao.isAdmin(accountUUID))
                && this.entryDao.isUnsubmitted(entryUUID)
                && this.fileDao.hasWorkBook(entryUUID)
                && this.fileDao.hasWorkBook(entryUUID);
    }

    public boolean canUpdate(
            final UUID accountUUID,
            final UUID entryUUID,
            final UUID updateToken
    ) {
        var hasRole    = this.hasRole(accountUUID, entryUUID);
        var checkToken = this.entryDao.existUpdateToken(entryUUID, updateToken);

        return hasRole && checkToken;
    }

    public void deleteEntry(
            final UUID entryUUID
    ) {
        if(this.isDeletablePhysically(entryUUID)) {
            // 一度も他のステータスに遷移していない、ファイルがアップロードされていないエントリーは物理削除
            this.entryRoleDao.deleteEntry(entryUUID);
            this.hEntryDao.deleteEntry(entryUUID);
            this.entryDao.delete(entryUUID);
        } else {
            // それ以外は論理削除して履歴として残す
            this.registerHistory(entryUUID);
            this.entryDao.deleteLogically(entryUUID);

            var files = this.fileDao.readEntryFiles(entryUUID);

            for(var file: files) {
                var fileUUID = file.getUuid();
                // ファイルを物理削除ではなく削除日付を記入し論理削除する
                var deletedAt = this.fileDao.deleteLogically(fileUUID);

                // ファイルの履歴を登録
                this.hFileDao.insert(
                        fileUUID,
                        file.getRevision() + 1,
                        file.getEntryUUID(),
                        file.getEntryRevision(),
                        file.getName(),
                        file.getType(),
                        file.getValidationUUID(),
                        file.getValidationStatus(),
                        file.getCreatedAt(),
                        // 削除日時と更新日時は同一のため
                        deletedAt,
                        deletedAt
                );
            }
        }
    }

    public EntryInformationResponse getEntryInformation(
            final UUID accountUUID,
            final UUID entryUUID
    ) {
        // 論理削除されているエントリーは対象外
        var record = this.entryDao.read(entryUUID);

        if(null == record) {
            return null;
        }

        var response = new EntryInformationResponse();

        response.setUuid(entryUUID);
        response.setRevision(record.getRevision());
        response.setLabel(record.getLabel());
        response.setType(record.getType());
        response.setStatus(record.getStatus());
        response.setValidationStatus(record.getValidationStatus());
        response.setUpdateToken(record.getUpdateToken());
        response.setPublishedRevision(record.getPublishedRevision());
        response.setPublishedAt(null == record.getPublishedAt() ? null : record.getPublishedAt().toString());
        response.setCreatedAt(record.getCreatedAt().toString());
        response.setUpdatedAt(record.getUpdatedAt().toString());

        // 編集画面のメニューのボタン押下できるか判断するフラグ群
        var menu = new MenuResponse();

        var status           = record.getStatus();
        var validationStatus = record.getValidationStatus();
        var hasWorkBook      = this.fileDao.hasWorkBook(entryUUID);

        // FIXME ステータスが確定したらEnumにステータスを切り出す

        // Validateボタンを押下できるのはActiveなExcelがアップロードされており、statusがUnsubmittedでvalidation_statusがUnvalidatedなこと
        menu.setValidate(hasWorkBook && "Unsubmitted".equals(status) && "Unvalidated".equals(validationStatus));
        // Submitボタンが押下できるのはstatusがUnsubmittedでvalidation_statusがValidなこと
        menu.setSubmit("Unsubmitted".equals(status) && "Valid".equals(validationStatus));
        //  Request to publicボタンが押下できるのは、validation_statusがValidなこと
        menu.setRequestToPublic("Valid".equals(validationStatus));
        //  Request to cancelボタンが押下できるのは、statusがSubmittedかPrivateだったら
        menu.setRequestToCancel("Submitted".equals(status) || "Private".equals(status));
        //  Request to updateボタンが押下できるのは、statusがPrivateかPublicだったら
        menu.setRequestToUpdate("Private".equals(status) || "Public".equals(status));

        response.setMenu(menu);

        var isAdmin = this.userDao.isAdmin(accountUUID);

        if(isAdmin) {
            // 編集画面の管理者メニューのボタンを押下できるか判断するフラグ群
            var adminMenu = new AdminMenuResponse();

            // To unsubmittedボタンを押下できるのはStatusがSubmittedだったら
            adminMenu.setToUnsubmitted("Submitted".equals(status));
            // To privateボタンを押下できるのはStatusがSubmittedだったら
            adminMenu.setToPrivate("Submitted".equals(status));
            // To publicボタンを押下できるのはStatusがSubmittedかPrivateだったら
            adminMenu.setToPublic("Submitted".equals(status) || "Private".equals(status));
            // To suppressedボタンを押下できるのはStatusがPublicだったら
            adminMenu.setToSupressed("Public".equals(status));
            // To killedボタンを押下できるのはStatusがPublicだったら
            adminMenu.setToKilled("Public".equals(status));
            // To replacedボタンを押下できるのはStatusがPublicだったら
            adminMenu.setToReplaced("Public".equals(status));

            response.setAdminMenu(adminMenu);
        }

        var files        = new ArrayList<FileResponse>();
        var fileEntities = this.fileDao.readEntryFiles(entryUUID);

        for(var entity : fileEntities) {
            var fileUUID = entity.getUuid();

            var file = new FileResponse();
            file.setUuid(fileUUID);
            file.setName(entity.getName());
            file.setUrl(UrlBuilder.url(config.api.baseUrl, "entry", entryUUID.toString(), "file", fileUUID.toString()).build());
            file.setValidationUuid(entity.getValidationUUID());
            file.setValidationStatus(entity.getValidationStatus());
            file.setType(entity.getType());

            files.add(file);
        }

        response.setFiles(files);

        var comments        = new ArrayList<CommentResponse>();
        var commentEntities = isAdmin
                ? this.commentDao.readEntryAllComments(entryUUID)
                : this.commentDao.readEntryComments(entryUUID);

        for(var entity : commentEntities) {
            var fileUUID = entity.getUuid();

            var comment = new CommentResponse();
            comment.setUuid(fileUUID);
            comment.setComment(entity.getComment());
            comment.setAdmin(entity.getAdmin());

            var author = this.accountDao.read(entity.getAccountUUID());

            comment.setAuthor(author.getUid());

            comments.add(comment);
        }

        response.setComments(comments);

        return response;
    }

    public void deleteFile(
        final UUID entryUUID,
        final String fileType,
        final String fileName
    ) {
        this.registerHistory(entryUUID);
        this.entryDao.updateRevision(entryUUID);
        this.entryDao.updateStatus(entryUUID, "Unsubmitted");
        this.entryDao.updateValidationStatus(entryUUID, "Unvalidated");

        var file     = this.fileDao.readByName(entryUUID, fileName, fileType);
        var fileUUID = file.getUuid();

        // ファイルを物理削除ではなく削除日付を記入し論理削除する
        var deletedAt = this.fileDao.deleteLogically(fileUUID);

        // ファイルの履歴を登録
        this.hFileDao.insert(
                fileUUID,
                file.getRevision() + 1,
                file.getEntryUUID(),
                file.getEntryRevision(),
                file.getName(),
                file.getType(),
                file.getValidationUUID(),
                file.getValidationStatus(),
                file.getCreatedAt(),
                deletedAt,
                deletedAt
        );
    }

    public boolean canUpload(
            final UUID entryUUID,
            final String fileType,
            final String fileName
    ) {
        var file = this.fileDao.readByName(entryUUID, fileName, fileType);

        if(null == file) {
            // ファイルが取得できなかったら、まだトークンやレコードが作成されてないのでアップロードできる
            return true;
        }

        var fileUUID = file.getUuid();

        return false == this.uploadDao.existActiveTokenByFileUUID(fileUUID);
    }

    public boolean canValidate(
            final UUID entryUUID
    ) {
        return this.fileDao.hasWorkBook(entryUUID);
    }

    public UploadTokenResponse getUploadToken(
            final UUID entryUUID,
            final String fileType,
            final String fileName
    ) {
        var file          = this.fileDao.readByName(entryUUID, fileName, fileType);
        var fileUUID      = null == file ? null : file.getUuid();
        var token         = this.uploadDao.create(fileUUID);

        var response = new UploadTokenResponse();
        response.setToken(token);

        return response;
    }

    public CommentResponse createComment(
        final UUID entryUUID,
        final UUID accountUUID,
        final String comment,
        final boolean admin
    ) {
        var uuid = this.commentDao.create(entryUUID, accountUUID, comment, admin);
        var response = new CommentResponse();
        response.setUuid(uuid);
        response.comment(comment);

        var account = this.accountDao.read(accountUUID);
        var author  = account.getUid();

        response.setAuthor(author);

        return response;
    }

    public CommentResponse editComment(
            final UUID accountUUID,
            final UUID commentUUID,
            final String comment,
            final boolean admin
    ) {
        this.commentDao.update(commentUUID, comment, admin);

        var response = new CommentResponse();
        response.setUuid(commentUUID);
        response.comment(comment);

        var account = this.accountDao.read(accountUUID);
        var author  = account.getUid();

        response.setAuthor(author);

        return response;
    }

    public void uploadFile(
            final UUID entryUUID,
            final String fileType,
            final String fileName,
            final UUID uploadToken,
            final MultipartFile multipartFile
            ) {
        var file          = this.fileDao.readByName(entryUUID, fileName, fileType);
        var fileUUID      = null == file ? null : file.getUuid();
        var revision      = null == file ? 1 : file.getRevision() + 1;

        this.registerHistory(entryUUID);
        this.entryDao.updateStatus(entryUUID, "Unsubmitted");
        this.entryDao.updateValidationStatus(entryUUID, "Unvalidated");
        var entry         = this.entryDao.read(entryUUID);
        var entryRevision = entry.getRevision();

        if(null == fileUUID) {
            // 新規アップロードならt_fileのレコードは存在しないので、ファイルのUUIDを発行する
            fileUUID = this.fileDao.create(entryUUID, fileName, fileType, entryRevision);
            file     = this.fileDao.read(fileUUID);
        } else {
            // 更新アップロードならt_fileのレコードのリビジョンを上げる
            // FIXME ステータスを外部定義化
            this.fileDao.update(fileUUID, revision, entryRevision, null, "Unvalidated");
        }

        // ファイルの履歴を登録
        this.hFileDao.insert(
                fileUUID,
                revision,
                file.getEntryUUID(),
                entryRevision,
                file.getName(),
                file.getType(),
                file.getValidationUUID(),
                file.getValidationStatus(),
                file.getCreatedAt(),
                file.getUpdatedAt(),
                file.getDeletedAt()
        );

        // 更新トークンを不活化、初回アップロードだとファイルUUIDも登録されていないので登録しておく
        this.uploadDao.update(uploadToken, fileUUID, true);

        var entryDirectory = this.fileModule.getPath(this.config.file.path.JVAR, entryUUID.toString());

        if(false == this.fileModule.exists(entryDirectory)) {
            // Entryのディレクトリがなかったら作る
            this.fileModule.createDirectory(entryDirectory);
        }

        // リビジョン付きのファイル名
        var uploadFileName = this.fileModule.getFileNameWithRevision(fileName, revision);

        // ファイルをアップロード
        var path = this.fileModule.getPath(this.config.file.path.JVAR, entryUUID.toString(), uploadFileName);
        this.fileModule.upload(this.fileModule.toByte(multipartFile), path);
    }

    public Resource downloadFile(
            final UUID entryUUID,
            final String fileType,
            final String fileName
    ) {
        var file = this.fileDao.readByName(entryUUID, fileName, fileType);

        if(null == file) {
            return null;
        }

        var rootPath = this.config.file.path.JVAR;
        var revision = file.getRevision();
        // リビジョン付きのファイル名
        var uploadFileName = this.fileModule.getFileNameWithRevision(fileName, revision);
        var path     = this.fileModule.getPath(rootPath, entryUUID.toString(), uploadFileName);

        return this.fileModule.download(path);
    }

    public boolean validateDuplicateWorkBook(
            final UUID entryUUID,
            final String fileType,
            final String fileName
    ) {
        if(false == "WORKBOOK".equals(fileType)) {
            return true;
        }

        return false == this.fileDao.hasOtherWorkBook(entryUUID, fileName);
    }

    public boolean validateUpdateToken(
            final UUID updateToken
    ) {
        return this.uploadDao.existActiveToken(updateToken);
    }

    public boolean canEditComment(
            final UUID accountUUID,
            final UUID commentUUID
    ) {
        var user = this.userDao.readByAccountUUID(accountUUID);
        var comment = this.commentDao.read(commentUUID);

        return user.isAdmin()
            || accountUUID.toString().equals(comment.getAccountUUID().toString());
    }

    public ValidationResponse validateMetadata(
            final UUID entryUUID
    ) {
        var response = new ValidationResponse();
        var workbook = this.fileDao.readEntryWorkBook(entryUUID);

        if(null == workbook) {
            response.setErrorMessage("This entry does'nt have a metadata(.xlsx)");

            return response;
        }

        // リビジョン付きのファイル名
        var uploadFileName = this.fileModule.getFileNameWithRevision(workbook.getName(), workbook.getRevision());
        var path           = this.fileModule.getPath(this.config.file.path.JVAR, entryUUID.toString(), uploadFileName);
        var file           = new FileSystemResource(path);

        var info = this.validationModule.validate(file);

        if (null == info) {
            response.setErrorMessage("Validation failed. Please contact us.");

            return response;
        }

        var validationUUID = info.getUuid();

        while(true) {
            var status = this.validationModule.getValidationStatus(validationUUID);

            if("finished".equals(status.getStatus())) {
                break;
            }

            if("error".equals(status.getStatus())) {
                response.setErrorMessage("Failed to get the validation result. Please contact us.");

                return response;
            }
        }

        var result = this.validationModule.getValidationResult(validationUUID);

        if (null == result) {
            response.setErrorMessage("Failed to get the validation result. Please contact us.");

            return response;
        }

        // FIXME バリデーション結果が明らかになったらHashMap<String, Object>は止める
        var stats            = (HashMap<String, Object>)result.getResult().get("stats");
        var errorCount       = (Integer)stats.get("error_count");
        var validationStatus = 0 == errorCount ? "Valid" : "Invalid";
        var metadataJson     = 0 == errorCount ? this.validationModule.getMetadataJson(validationUUID) : null;

        this.fileDao.update(
            workbook.getUuid(),
            workbook.getRevision(),
            workbook.getEntryRevision() + 1,
            validationUUID,
            validationStatus
        );

        this.entryDao.updateValidationStatus(
                entryUUID,
                validationStatus,
                metadataJson
        );

        return response;
    }

    public void submitEntry(final UUID entryUUID) {
        this.registerHistory(entryUUID);
        this.entryDao.submit(entryUUID);
    }

    public void registerHistory(final UUID entryUUID) {
        var entry = this.entryDao.read(entryUUID);
        this.hEntryDao.insert(
                entryUUID,
                entry.getLabel(),
                entry.getType(),
                entry.getStatus(),
                entry.getValidationStatus(),
                entry.getMetadataJson(),
                entry.getAggregateJson(),
                entry.getEditable(),
                entry.getPublishedRevision(),
                entry.getPublishedAt()
        );
    }

}
