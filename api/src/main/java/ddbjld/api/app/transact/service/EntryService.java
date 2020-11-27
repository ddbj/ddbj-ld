package ddbjld.api.app.transact.service;

import ddbjld.api.app.transact.dao.EntryDao;
import ddbjld.api.app.transact.dao.EntryRoleDao;
import ddbjld.api.app.transact.dao.HEntryDao;
import ddbjld.api.data.model.v1.entry.jvar.EntriesResponse;
import ddbjld.api.data.model.v1.entry.jvar.EntryInformationResponse;
import ddbjld.api.data.model.v1.entry.jvar.EntryResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            final UUID entryUUID
    ) {
        return null;
    }
}
