package com.ddbj.ld.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity {
    /** t_file.uuid */
    private UUID uuid;
    /**t_file.revision */
    private Integer revision;
    /** t_file.entry_uuid */
    private UUID entryUUID;
    /** t_file.entry_revision */
    private Integer entryRevision;
    /** t_file.name */
    private String name;
    /** t_file.type */
    private String type;
    /** t_file.validation_uuid */
    private UUID validationUUID;
    /** t_file.validation_status */
    private String validationStatus;
    /** t_file.created_at */
    private LocalDateTime createdAt;
    /** t_file.updated_at */
    private LocalDateTime updatedAt;
    /** t_file.deleted_at */
    private LocalDateTime deletedAt;
}
