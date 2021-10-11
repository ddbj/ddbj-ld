package com.ddbj.ld.data.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfo {
    private String accessToken;
    private String mail;
    private String uid;
    private String grantType;
    private String[] scope;
    private String realm;
    private String tokenType;
    private String expiresIn;
    private String clientId;
}
