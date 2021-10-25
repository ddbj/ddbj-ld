package com.ddbj.ld.common.constants;

import lombok.AllArgsConstructor;

// 公開メタデータに入れるステータス https://ddbj-dev.atlassian.net/browse/RESOURCE-181
@AllArgsConstructor
public enum StatusEnum {
    PUBLIC("public"),
    CONFIDENTIAL("confidential"),
    SUPPRESSED("suppressed"),
    UNPUBLISHED("unpublished"),
    KILLED("killed")
    ;

    public final String status;
}
