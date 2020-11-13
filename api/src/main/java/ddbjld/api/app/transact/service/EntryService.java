package ddbjld.api.app.transact.service;

import ddbjld.api.data.model.v1.entry.jvar.EntriesResponse;
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

    public EntryResponse createEntry(
            final UUID accountUUID,
            final String title,
            final String description
    ) {
        return null;
    }

    public EntriesResponse getEntries(
            final UUID accountUUID
    ) {
        return null;
    }
}
