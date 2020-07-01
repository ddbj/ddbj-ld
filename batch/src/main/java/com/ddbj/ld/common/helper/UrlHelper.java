package com.ddbj.ld.common.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UrlHelper {
    public String getUrl(String type, String identifier) {
        return "https%3A%2F%2Fddbj.nig.ac.jp%2Fresource%2F" + type + "%2F" + identifier;
    }

    public String getUrl(String type, String identifier, String extension) {
        return "https%3A%2F%2Fddbj.nig.ac.jp%2Fresource%2F" + type + "%2F" + identifier + extension;
    }
}
