package com.ddbj.ld.data.beans.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownloadUrlBean {
    private String type;

    private String name;

    private String url;

    private String ftpUrl;
}
