package com.ddbj.ld.data.bean;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoginInfo {
    private String accessToken;
    private String refreshToken;
    private String scope;
    private String tokenType;
    private String expiresIn;
}
