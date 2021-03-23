package ddbjld.api.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntryRoleEntity {
    /** t_entry_role.account_uuid */
    private UUID accountUUID;
    /** t_entry_role.entry_uuid */
    private UUID entryUUID;
}
