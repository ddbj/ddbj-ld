package ddbjld.api.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentEntity {
    /** t_comment.uuid */
    private UUID uuid;
    /** t_comment.entry_uuid */
    private UUID entryUUID;
    /** t_comment.account_uuid */
    private UUID accountUUID;
    /** t_comment.admin */
    private Boolean admin;
    /** t_comment.comment */
    private String comment;
    /** t_comment.created_at */
    private LocalDateTime createdAt;
    /** t_comment.updated_at */
    private LocalDateTime updatedAt;
}
