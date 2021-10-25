package com.ddbj.ld.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestEntity {
    /** t_request.uuid */
    private UUID uuid;
    /** t_request.entry_uuid */
    private UUID entryUUID;
    /** t_request.entry_revision */
    private Integer entryRevision;
    /** t_request.account_uuid */
    private UUID accountUUID;
    /** t_request.type */
    private String type;
    /** t_request.comment */
    private String comment;
    /** t_request.status */
    private String status;
    /** t_request.created_at */
    private LocalDateTime createdAt;
    /** t_request.updated_at */
    private LocalDateTime updatedAt;
}
