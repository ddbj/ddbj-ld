package ddbjld.api.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HEntryEntity {
    /** h_entry.uuid */
    private UUID uuid;
    /** h_entry.label */
    private String label;
    /** h_entry.revision */
    private Integer revision;
    /** h_entry.type */
    private String type;
    /** h_entry.status */
    private String status;
    /** h_entry.validation_status */
    private String validationStatus;
    /** h_entry.metadata_json */
    private String metadataJson;
    /** h_entry.aggregateJson */
    private String aggregateJson;
    /** h_entry.editable */
    private Boolean editable;
    /** h_entry.published_revision */
    private Integer publishedRevision;
    /** h_entry.published_at */
    private LocalDateTime publishedAt;
    /** h_entry.created_at */
    private LocalDateTime createdAt;
    /** h_entry.updated_at */
    private LocalDateTime updatedAt;
}
