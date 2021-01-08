package ddbjld.api.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HFileEntity {
    /** h_file.uuid */
    private UUID uuid;
    /**h_file.revision */
    private Integer revision;
    /** h_file.entry_uuid */
    private UUID entryUUID;
    /** h_file.entry_revision */
    private Integer entryRevision;
    /** h_file.name */
    private String name;
    /** h_file.type */
    private String type;
    /** h_file.validation_uuid */
    private UUID validationUUID;
    /** h_file.validation_status */
    private String validationStatus;
    /** h_file.created_at */
    private LocalDateTime createdAt;
    /** h_file.updated_at */
    private LocalDateTime updatedAt;
    /** h_file.deleted_at */
    private LocalDateTime deletedAt;
}
