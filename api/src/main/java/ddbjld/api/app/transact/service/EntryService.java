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

    private RequestDao requestDao;

    private FileModule fileModule;

    private ValidationModule validationModule;

    private ConfigSet config;

    public EntryResponse createEntry(final UUID accountUUID, final String type) {
        var entryUUID = this.entryDao.create(type);
        // FIXME 外だし
        this.registerHistory(entryUUID, "Create");

        this.entryRoleDao.insert(accountUUID, entryUUID);

        var response = new EntryResponse();
        response.setUuid(entryUUID);
        // FIXME 外だし
        response.setStatus("Unsubmitted");
        // FIXME 外だし
        response.setActiveRequest("Not exists");

        var entry = this.entryDao.read(entryUUID);

        response.setUpdateToken(entry.getUpdateToken());

        return response;
    }

    public RequestResponse createRequest(
            final UUID accountUUID,
            final UUID entryUUID,
            final String type,
            final String comment
    ) {
        this.entryDao.updateRevision(entryUUID);
        var entry = this.entryDao.read(entryUUID);

        if(null == entry) {
            return null;
        }

        this.registerHistory(entryUUID, "Request to" + type);

        var uuid    = this.requestDao.create(entryUUID, entry.getRevision(), accountUUID, type, comment);
        var account = this.accountDao.read(accountUUID);

        var response = new RequestResponse();

        response.setUuid(uuid);
        response.setType(type);
        // フィルター用にnullからから文字に変換
        response.setComment(null == comment ? "" : comment);
        response.setStatus("open");
        response.setAuthor(account.getUid());
        response.setIsEditable(true);
        response.setIsCancelable(true);

        var user = this.userDao.readByAccountUUID(accountUUID);

        response.setIsApplyable(user.isCurator());

        return response;
    }

    public void applyRequest(
            final UUID entryUUID,
            final UUID requestUUID) {
        var request = this.requestDao.read(requestUUID);
        var type    = request.getType();
        var entryStatus = "";

        if("PUBLIC".equals(type)) {
            entryStatus = "Public";
        }

        if("PRIVATE".equals(type)) {
            entryStatus = "Private";
        }

        if("CANCEL".equals(type)) {
            var entry = this.entryDao.read(entryUUID);
            entryStatus = entry.getStatus().equals("Submitted") ? "Unsubmitted" : "Cancel";
        }

        if("UPDATE".equals(type)) {
            entryStatus = "Unsubmitted";
        }

        // FIXME Publicになったら一旦編集できないようにする？（t_entry.editable = false）
        this.entryDao.updateStatus(entryUUID, entryStatus);
        this.entryDao.updateRevision(entryUUID);
        this.registerHistory(entryUUID, "Apply a request to " + type.toLowerCase());

        this.requestDao.close(requestUUID);

        // FIXME Elasticsearchなど非トランザクション系の処理
    }

    public void cancelRequest(
            final UUID entryUUID,
            final UUID requestUUID) {
        this.entryDao.updateRevision(entryUUID);
        this.registerHistory(entryUUID, "Cancel a request");

        this.requestDao.cancel(requestUUID);
    }

    public void deleteComment(
            final UUID entryUUID,
            final UUID commentUUID
    ) {
        this.entryDao.updateRevision(entryUUID);
        this.registerHistory(entryUUID, "Delete a comment");

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
            entry.setStatus(record.getStatus());

            var activeRequest = false == this.requestDao.hasNoActiveRequest(entryUUID) ? "Exists" : "None";

            entry.setActiveRequest(activeRequest);
            entry.setIsDeletable(this.isDeletable(accountUUID, entryUUID));
            entry.setUpdateToken(entry.getUpdateToken());

            response.add(entry);
        }

        // label順でソートする
        Collections.sort(response, new Comparator<EntryResponse>(){
            public int compare(EntryResponse r1, EntryResponse r2) {
                var label1 = r1.getLabel();
                var label2 = r2.getLabel();

                if (this.compareLength(label1, label2) == 0) {
                    return label1.compareTo(label2);
                } else {
                    return this.compareLength(label1, label2);
                }
            }

            private int compareLength(String label1, String label2) {
                if (label1.length() > label2.length()) {
                    return 1;
                } else if (label1.length() < label2.length()) {
                    return -1;
                } else {
                    return 0;
                }
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

            var activeRequest = false == this.requestDao.hasNoActiveRequest(uuid) ? "Exists" : "None";

            entry.setActiveRequest(activeRequest);
            entry.setIsDeletable(this.isDeletable(accountUUID, uuid));
            entry.setUpdateToken(record.getUpdateToken());

            response.add(entry);
        }

        // label順でソートする
        Collections.sort(response, new Comparator<EntryResponse>(){
            public int compare(EntryResponse r1, EntryResponse r2) {
                var label1 = r1.getLabel();
                var label2 = r2.getLabel();

                if (this.compareLength(label1, label2) == 0) {
                    return label1.compareTo(label2);
                } else {
                    return this.compareLength(label1, label2);
                }
            }

            private int compareLength(String label1, String label2) {
                if (label1.length() > label2.length()) {
                    return 1;
                } else if (label1.length() < label2.length()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        return response;
    }

    public boolean hasRole(
            final UUID accountUUID,
            final UUID entryUUID
    ) {
        var hasRole = this.entryRoleDao.hasRole(accountUUID, entryUUID);
        var isCurator = this.userDao.isCurator(accountUUID);

        return hasRole || isCurator;
    }

    public boolean isDeletable(
            final UUID accountUUID,
            final UUID entryUUID
    ) {
        return (this.entryRoleDao.hasRole(accountUUID, entryUUID)
             || this.userDao.isCurator(accountUUID))
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
                || this.userDao.isCurator(accountUUID))
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

    public boolean canRequest(
            final UUID accountUUID,
            final UUID entryUUID,
            final String type
    ) {
        var entry = this.entryDao.read(entryUUID);

        if(null == entry) {
            return false;
        }

        var validationStatus = entry.getValidationStatus();
        var status = entry.getStatus();

        var hasRole    = this.hasRole(accountUUID, entryUUID);
        var canRequest = false;

        var hasNoActiveRequest = this.requestDao.hasNoActiveRequest(entryUUID);

        if("PUBLIC".equals(type)) {
            canRequest = hasRole && "Valid".equals(validationStatus) && hasNoActiveRequest;
        }

        if("Cancel".equals(type)) {
            canRequest = hasRole && ("Submitted".equals(status) || "Private".equals(status)) && hasNoActiveRequest;
        }

        if("UPDATE".equals(type)) {
            canRequest = hasRole && ("Private".equals(status) || "Public".equals(status)) && hasNoActiveRequest;
        }

        return canRequest;
    }

    public boolean canApplyRequest(
            final UUID accountUUID,
            final UUID requestUUID
    ) {
        var user    = this.userDao.readByAccountUUID(accountUUID);
        var request = this.requestDao.read(requestUUID);

        if(null == user || null == request) {
            return false;
        }

        var hasOpen = "Open".equals(request.getStatus());
        var isCurator = user.isCurator();

        return hasOpen && isCurator;
    }

    public boolean canCancelRequest(
            final UUID accountUUID,
            final UUID entryUUID,
            final UUID requestUUID
    ) {
        var user    = this.userDao.readByAccountUUID(accountUUID);
        var request = this.requestDao.read(requestUUID);

        if(null == user || null == request) {
            return false;
        }

        var hasOpen = "Open".equals(request.getStatus());
        var isCurator = user.isCurator();
        var hasRole   = this.hasRole(accountUUID, entryUUID);
        var isAuthor  = accountUUID.equals(request.getAccountUUID());

        // リクエストがOPENの状態で、リクエストしたアカウントがキュレーターかもしくはエントリーに所属していて、リクエスト作者と同じ
        return hasOpen && (isCurator || (hasRole && isAuthor));
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
            this.entryDao.deleteLogically(entryUUID);
            this.registerHistory(entryUUID, "Delete");

            var files = this.fileDao.readEntryFiles(entryUUID);

            for(var file: files) {
                var fileUUID = file.getUuid();
                this.fileDao.deleteLogically(fileUUID);

                // ファイルの履歴を登録
                this.registerFileHistory(fileUUID, "Delete");
            }
        }
    }

    public EntryInfoResponse getEntryInfo(
            final UUID accountUUID,
            final UUID entryUUID
    ) {
        // 論理削除されているエントリーは対象外
        var record = this.entryDao.read(entryUUID);

        if(null == record) {
            return null;
        }

        var response = new EntryInfoResponse();

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

        var reqMenu = new RequestMenuResponse();
        var hasNoActiveRequest = this.requestDao.hasNoActiveRequest(entryUUID);
        // 公開リクエストチェックボックスが押せるかどうか
        var requestToPublic = "Valid".equals(validationStatus) && hasNoActiveRequest && ("unsubmitted".equals(status) || "Submitted".equals(status));
        // キャンセルリクエストチェックボックスが押せるかどうか
        var requestToCancel = ("Submitted".equals(status) || "Private".equals(status)) && hasNoActiveRequest;
        // 更新リクエストチェックボックスが押せるかどうか
        var requestToUpdate = ("Private".equals(status) || "Public".equals(status))&& hasNoActiveRequest;
        // リクエストボタンが押せるかどうか
        reqMenu.setRequest((requestToPublic || requestToCancel || requestToUpdate) && hasNoActiveRequest);
        reqMenu.setRequestToPublic(requestToPublic);
        reqMenu.setRequestToCancel(requestToCancel);
        reqMenu.setRequestToUpdate(requestToUpdate);

        menu.setRequestMenu(reqMenu);

        response.setMenu(menu);

        var isCurator = this.userDao.isCurator(accountUUID);

        // 編集画面の管理者メニューのボタンを押下できるか判断するフラグ群
        var curatorMenu = new CuratorMenuResponse();

        // To unsubmittedボタンを押下できるのはStatusがSubmittedだったら
        curatorMenu.setToUnsubmitted(isCurator && "Submitted".equals(status));
        // To privateボタンを押下できるのはStatusがSubmittedだったら
        curatorMenu.setToPrivate(isCurator && "Submitted".equals(status));
        // To publicボタンを押下できるのはStatusがSubmittedかPrivateだったら
        curatorMenu.setToPublic(isCurator && ("Submitted".equals(status) || "Private".equals(status)));
        // To suppressedボタンを押下できるのはStatusがPublicだったら
        curatorMenu.setToSuppressed(isCurator && "Public".equals(status));
        // To killedボタンを押下できるのはStatusがPublicだったら
        curatorMenu.setToKilled(isCurator && "Public".equals(status));
        // To replacedボタンを押下できるのはStatusがPublicだったら
        curatorMenu.setToReplaced(isCurator && "Public".equals(status));

        response.setCuratorMenu(curatorMenu);

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
        var commentEntities = isCurator
                ? this.commentDao.readEntryAllComments(entryUUID)
                : this.commentDao.readEntryComments(entryUUID);

        for(var entity : commentEntities) {
            var comment = new CommentResponse();

            comment.setUuid(entity.getUuid());
            comment.setComment(entity.getComment());

            var curator = entity.getCurator();
            comment.setVisibility(curator ? "Curator Only": "Everyone");
            comment.setCurator(curator);

            var author = this.accountDao.read(entity.getAccountUUID());

            comment.setAuthor(author.getUid());

            comments.add(comment);
        }

        response.setComments(comments);

        var requests = new ArrayList<RequestResponse>();
        var reqEntities = this.requestDao.readEntryAllRequests(entryUUID);

        for(var entity : reqEntities) {
            var reqUUID = entity.getUuid();

            var request = new RequestResponse();
            request.setUuid(reqUUID);
            request.setType("To " + entity.getType().toLowerCase());
            request.setStatus(entity.getStatus());
            // フィルター用にnullを空文字に変換
            request.setComment(null == entity.getComment() ? "" : entity.getComment());
            // リクエスト作成者のUUID
            var authorUUID = entity.getAccountUUID();
            var author = this.accountDao.read(authorUUID);

            request.setAuthor(author.getUid());

            var isAuthor     = accountUUID.equals(authorUUID);
            var isOpen       = "Open".equals(request.getStatus());

            var isEditable = isCurator || isAuthor;
            var isCancelable = isOpen && (isCurator || isAuthor);
            var isApplyable  = isCurator && isOpen;

            request.setIsEditable(isEditable);

            request.setIsCancelable(isCancelable);
            request.setIsApplyable(isApplyable);

            requests.add(request);
        }

        response.setRequests(requests);

        return response;
    }

    public void deleteFile(
        final UUID entryUUID,
        final String fileType,
        final String fileName
    ) {
        this.entryDao.resetStatus(entryUUID);
        this.registerHistory(entryUUID, "Delete a file: " + fileName);

        var file     = this.fileDao.readByName(entryUUID, fileName, fileType);
        var fileUUID = file.getUuid();

        // ファイルを物理削除ではなく削除日付を記入し論理削除する
        this.fileDao.deleteLogically(fileUUID);

        // ファイルの履歴を登録
        this.registerFileHistory(fileUUID, "Delete");
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
        final boolean curator
    ) {
        this.entryDao.updateRevision(entryUUID);
        this.registerHistory(entryUUID, "Post a comment");

        var uuid = this.commentDao.create(entryUUID, accountUUID, comment, curator);

        if(null == uuid) {
            log.error("Creating comment is failed.");
            return null;
        }

        var response = new CommentResponse();
        response.setUuid(uuid);
        response.comment(comment);
        response.setVisibility(curator ? "Curator Only" : "Everyone");

        var account = this.accountDao.read(accountUUID);
        var author  = account.getUid();

        response.setAuthor(author);

        return response;
    }

    public CommentResponse editComment(
            final UUID accountUUID,
            final UUID entryUUID,
            final UUID commentUUID,
            final String comment,
            final boolean curator
    ) {
        this.entryDao.updateRevision(entryUUID);
        this.registerHistory(entryUUID, "Edit a comment");

        this.commentDao.update(commentUUID, comment, curator);

        var response = new CommentResponse();
        response.setUuid(commentUUID);
        response.comment(comment);
        response.setVisibility(curator ? "Curator Only" : "Everyone");

        var account = this.accountDao.read(accountUUID);
        var author  = account.getUid();

        response.setAuthor(author);

        return response;
    }

    public RequestResponse editRequest(
            final UUID accountUUID,
            final UUID entryUUID,
            final UUID requestUUID,
            final String comment
    ) {
        var request = this.requestDao.read(requestUUID);

        if(null == request) {
            return null;
        }

        this.entryDao.updateRevision(entryUUID);
        this.registerHistory(entryUUID, "Edit a request");

        this.requestDao.updateComment(requestUUID, comment);

        var response = new RequestResponse();
        response.setUuid(requestUUID);
        response.setType("to " + request.getType().toLowerCase());
        response.comment(comment);

        var account = this.accountDao.read(accountUUID);
        var author  = account.getUid();

        response.setAuthor(author);

        response.setIsEditable(true);

        var isOpen = "Open".equals(request.getStatus());

        response.setIsCancelable(isOpen);

        var user = this.userDao.readByAccountUUID(accountUUID);

        response.setIsApplyable(user.isCurator() && isOpen);

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
        var entryAction   = 1 == revision ? "Upload a file:" + fileName : "Update a file:" + fileName;

        this.entryDao.resetStatus(entryUUID);
        this.registerHistory(entryUUID, entryAction);
        var entry         = this.entryDao.read(entryUUID);
        var entryRevision = entry.getRevision();

        if(null == fileUUID) {
            // 新規アップロードならt_fileのレコードは存在しないので、ファイルのUUIDを発行する
            fileUUID = this.fileDao.create(entryUUID, fileName, fileType, entryRevision);
        } else {
            // 更新アップロードならt_fileのレコードのリビジョンを上げる
            // FIXME ステータスを外部定義化
            this.fileDao.update(fileUUID, revision, entryRevision, null, "Unvalidated");
        }

        // ファイルの履歴を登録
        var fileAction   = 1 == revision ? "Upload" : "Update";
        this.registerFileHistory(fileUUID, fileAction);

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

        return user.isCurator()
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
        var fileUUID = workbook.getUuid();

        this.fileDao.update(
            fileUUID,
            // ステータスは変わったがファイルの内容は変わっていないため、ファイルのリビジョンは上げない
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

        var action = "Validate";
        this.registerHistory(entryUUID, action);

        return response;
    }

    public void submitEntry(final UUID entryUUID) {
        this.entryDao.submit(entryUUID);
        this.registerHistory(entryUUID, "Submit");
    }

    public void registerHistory(final UUID entryUUID, final String action) {
        var entry = this.entryDao.readAny(entryUUID);
        this.hEntryDao.insert(
                entryUUID,
                entry.getRevision(),
                entry.getLabel(),
                entry.getType(),
                entry.getStatus(),
                entry.getValidationStatus(),
                entry.getMetadataJson(),
                entry.getAggregateJson(),
                entry.getEditable(),
                entry.getPublishedRevision(),
                entry.getPublishedAt(),
                action
        );
    }

    public void registerFileHistory(final UUID fileUUID, final String action) {
        var file = this.fileDao.readAny(fileUUID);
        this.hFileDao.insert(
                fileUUID,
                file.getRevision(),
                file.getEntryUUID(),
                file.getEntryRevision(),
                file.getName(),
                file.getType(),
                file.getValidationUUID(),
                file.getValidationStatus(),
                file.getCreatedAt(),
                file.getUpdatedAt(),
                file.getDeletedAt(),
                action
        );
    }
}
