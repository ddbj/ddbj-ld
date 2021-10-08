package com.ddbj.ld.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadEntity {
    /** t_upload.uuid */
    private UUID uuid;
    /** t_upload.file_uuid */
    private UUID entryUUID;
    /** t_upload.ended */
    private boolean ended;
}
