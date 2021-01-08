package ddbjld.api.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntryEntity {
    /** t_entry.uuid */
    private UUID uuid;
    /**t_entry.revision */
    private Integer revision;
    /** t_entry.label */
    private String label;
    /** t_entry.title */
    private String title;
    /** t_entry.description */
    private String description;
    /** t_entry.status */
    private String status;
    /** t_entry.validation_status */
    private String validationStatus;
    /** t_entry.metadata_json */
    private String metadataJson;
    /** t_entry.aggregateJson */
    private String aggregateJson;
    /** t_entry.editable */
    private Boolean editable;
    /** t_entry.update_token */
    private UUID updateToken;
    /** t_entry.published_revision */
    private Integer publishedRevision;
    /** t_entry.published_at */
    private LocalDateTime publishedAt;
    /** t_entry.created_at */
    private LocalDateTime createdAt;
    /** t_entry.updated_at */
    private LocalDateTime updatedAt;
}
