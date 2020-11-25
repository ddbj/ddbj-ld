package ddbjld.api.app.transact.service;

import ddbjld.api.app.config.ConfigSet;
import ddbjld.api.app.transact.dao.*;
import ddbjld.api.common.utility.UrlBuilder;
import ddbjld.api.data.model.v1.entry.jvar.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private CommentDao commentDao;

    private ConfigSet config;

    public EntryResponse createEntry(
            final UUID accountUUID,
            final String title,
            final String description
    ) {
        var entryUUID = this.entryDao.create(title, description);
        this.hEntryDao.insert(
                entryUUID,
                null,
                title,
                description,
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
        response.setTitle(title);
        response.setDescription(description);
        // FIXME 外だし
        response.setStatus("Unsubmitted");
        // FIXME 外だし
        response.setValidationStatus("Unvalidated");

        return response;
    }

    public EntriesResponse getEntries(
            final UUID accountUUID
    ) {
        // FIXME アカウントが管理者の場合は全てのエントリーを取得する
        //  - 別のメソッドでもいいかも…

        var roles = this.entryRoleDao.readByAccountUUID(accountUUID);

        var response = new EntriesResponse();

        for(var role : roles) {
            var entryUUID   = role.getEntryUUID();
            var record      = this.entryDao.read(entryUUID);

            var entry     = new EntryResponse();

            entry.setUuid(entryUUID);
            entry.setTitle(record.getTitle());
            entry.setDescription(record.getDescription());
            entry.setValidationStatus(record.getValidationStatus());
            entry.setStatus(record.getStatus());
            entry.setIsDeletable(this.isDeletable(accountUUID, entryUUID));

            response.add(entry);
        }

        return response;
    }

    public boolean hasRole(
            final UUID accountUUID,
            final UUID entryUUID
    ) {
        return true;
    }

    public boolean isDeletable(
            final UUID accountUUID,
            final UUID entryUUID
    ) {
        return this.entryRoleDao.hasRole(accountUUID, entryUUID)
            && this.entryDao.isUnsubmitted(entryUUID)
            && this.hEntryDao.countByUUID(entryUUID) == 1;
    }

    public void deleteEntry(
            final UUID entryUUID
    ) {
        // FIXME NextCloudにアップロードしたファイルを削除する処理
        this.entryRoleDao.deleteEntry(entryUUID);
        this.hEntryDao.deleteEntry(entryUUID);
        this.entryDao.delete(entryUUID);
    }

    public EntryInformationResponse getEntryInformation(
            final UUID accountUUID,
            final UUID entryUUID
    ) {
        var entry = this.entryDao.read(entryUUID);

        if(null == entry) {
            return null;
        }

        var response = new EntryInformationResponse();

        response.setUuid(entryUUID);
        response.setLabel(entry.getLabel());
        response.setTitle(entry.getTitle());
        response.setDescription(entry.getDescription());
        response.setStatus(entry.getStatus());
        response.setValidationStatus(entry.getValidationStatus());

        // 編集画面のメニューのボタン押下できるか判断するフラグ群
        var menu = new MenuResponse();

        var status = entry.getStatus();
        var validationStatus = entry.getValidationStatus();
        var hasWorkBook = this.fileDao.hasWorkBook(entryUUID);

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
            file.setType(entity.getType());

            file.setUrl(UrlBuilder.url(config.api.baseUrl, "entry", entryUUID.toString(), "file", fileUUID.toString()).build());

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
}
