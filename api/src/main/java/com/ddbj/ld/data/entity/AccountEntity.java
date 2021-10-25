package com.ddbj.ld.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {
    /** t_account.uuid */
    private UUID uuid;
    /** t_account.uid */
    private String uid;
    /** t_account.refresh_token */
    private String refreshToken;
}
