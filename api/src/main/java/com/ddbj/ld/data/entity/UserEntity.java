package com.ddbj.ld.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    /** t_user.uuid */
    private UUID uuid;
    /** t_user.account_uuid */
    private UUID accountUUID;
    /** t_user.curator */
    private boolean curator;
}
