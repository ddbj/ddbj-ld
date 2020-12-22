package ddbjld.api.app.transact.service;

import ddbjld.api.app.config.ConfigSet;
import ddbjld.api.app.core.module.FileModule;
import ddbjld.api.app.transact.dao.*;
import ddbjld.api.common.utility.UrlBuilder;
import ddbjld.api.data.model.v1.entry.jvar.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.UUID;

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

    private FileModule fileModule;

    private CommentDao commentDao;

    private UploadDao uploadDao;

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
            var record      = this.entryDao.read(entryUUID);

            var entry       = new EntryResponse();

            entry.setUuid(entryUUID);
            entry.setLabel(record.getLabel());
            entry.setType(record.getType());
            entry.setValidationStatus(record.getValidationStatus());
            entry.setStatus(record.getStatus());
            entry.setIsDeletable(this.isDeletable(accountUUID, entryUUID));

            response.add(entry);
        }

        return response;
    }

    // 管理者用全エントリー取得メソッド
    public EntriesResponse getAllEntries(
            final UUID accountUUID
    ) {
        var response = new EntriesResponse();
        // アカウントが管理者の場合は全てのエントリーを取得する
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
            && this.entryDao.isUnsubmitted(entryUUID)
            && this.hEntryDao.countByUUID(entryUUID) == 1;
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

    public void deleteEntry(
            final UUID entryUUID
    ) {
        this.entryRoleDao.deleteEntry(entryUUID);
        this.hEntryDao.deleteEntry(entryUUID);
        this.entryDao.delete(entryUUID);
    }

    public EntryInformationResponse getEntryInformation(
            final UUID accountUUID,
            final UUID entryUUID
    ) {
        var record = this.entryDao.read(entryUUID);

        if(null == record) {
            return null;
        }

        var response = new EntryInformationResponse();

        response.setUuid(entryUUID);
        response.setLabel(record.getLabel());
        response.setType(record.getType());
        response.setStatus(record.getStatus());
        response.setValidationStatus(record.getValidationStatus());

        // 編集画面のメニューのボタン押下できるか判断するフラグ群
        var menu = new MenuResponse();

        var status           = record.getStatus();
        var validationStatus = record.getValidationStatus();
        var hasWorkBook      = this.fileDao.hasWorkBook(entryUUID);
        var hasVCF           = this.fileDao.hasVCF(entryUUID);

        // FIXME ステータスが確定したらEnumにステータスを切り出す

        // Validateボタンを押下できるのはActiveなExcelがアップロードされており、statusがUnsubmittedでvalidation_statusがUnvalidatedなこと
        menu.setValidate(hasWorkBook && hasVCF && "Unsubmitted".equals(status) && "Unvalidated".equals(validationStatus));
        // Submitボタンが押下できるのはstatusがUnsubmittedでvalidation_statusがValidなこと
        menu.setSubmit("Unsubmitted".equals(status) && "Valid".equals(validationStatus));
        //  Request to publicボタンが押下できるのは、validation_statusがValidなこと
        menu.setRequestToPublic("Valid".equals(validationStatus));
        //  Request to cancelボタンが押下できるのは、statusがSubmittedかPrivateだったら
        menu.setRequestToCancel("Submitted".equals(status) || "Private".equals(status));
        //  Request to updateボタンが押下できるのは、statusがPrivateかPublicだったら
        menu.setRequestToUpdate("Private".equals(status) || "Public".equals(status));

        response.setMenu(menu);

        if(this.userDao.isAdmin(accountUUID)) {
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
        var commentEntities = this.commentDao.readEntryComments(entryUUID);

        for(var entity : commentEntities) {
            var fileUUID = entity.getUuid();

            var comment = new CommentResponse();
            comment.setUuid(fileUUID);
            comment.setComment(entity.getComment());

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
        // TODO 実装
        //  - 削除の仕様は不確定なので保留
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
        return this.fileDao.hasWorkBook(entryUUID) && this.fileDao.hasVCF(entryUUID);
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
        final String comment
    ) {
        var uuid = this.commentDao.create(entryUUID, accountUUID, comment);
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
            final String comment
    ) {
        this.commentDao.update(commentUUID, comment);

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
                file.getUpdatedAt()
        );

        // 更新トークンを不活化、初回アップロードだとファイルUUIDも登録されていないので登録しておく
        this.uploadDao.update(uploadToken, fileUUID, true);

        var entryDirectory = this.fileModule.getPath(this.config.file.path.JVAR, entryUUID.toString());

        if(false == this.fileModule.exists(entryDirectory)) {
            // Entryのディレクトリがなかったら作る
            this.fileModule.createDirectory(entryDirectory);
        }

        // ファイルをアップロード
        var path = this.fileModule.getPath(this.config.file.path.JVAR, entryUUID.toString(), fileName + "." + revision);
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
        var path     = this.fileModule.getPath(rootPath, entryUUID.toString(), fileName + "." + revision);

        return this.fileModule.download(path);
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
        var workbook  = this.fileDao.readEntryWorkBook(entryUUID);

        if(null == workbook) {
            response.setErrorMessage("This entry does'nt have a metadata(.xlsx)");

            return response;
        }

        var files = this.fileDao.readEntryFiles(entryUUID);

        for(var file: files) {
            // FIXME とりあえずvalidationステータスをOKにしているので正式な処理を実装する
            this.fileDao.update(
                    file.getUuid(),
                    file.getRevision(),
                    file.getEntryRevision() + 1,
                    UUID.randomUUID(),
                    "Valid"
            );
        }

        this.entryDao.updateValidationStatus(
                entryUUID,
                "Valid"
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
