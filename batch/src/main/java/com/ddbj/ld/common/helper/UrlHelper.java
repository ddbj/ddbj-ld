package com.ddbj.ld.common.helper;

import com.ddbj.ld.common.annotation.Helper;
import com.ddbj.ld.config.ConfigSet;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Helper
public class UrlHelper {
    private final ConfigSet config;

    public String getUrl(String type, String identifier) {
        return this.config.other.resourceUrl + type + "/" + identifier;
    }

    public String getUrl(String type, String identifier, String extension) {
        return this.config.other.resourceUrl + type + "/" + identifier + extension;
    }
}
