package com.ddbj.ld.data.bean;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming
public class AmAdminInfo {
    private String tokenId;
    private String successUrl;
    private String realm;
}
