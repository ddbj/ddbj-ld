package com.ddbj.ld.common.helper;

import com.ddbj.ld.common.setting.Settings;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class UrlHelper {
    private Settings settings;

    public String getUrl(String type, String identifier) {
        return settings.getResourceUrl() + type + "/" + identifier;
    }

    public String getUrl(String type, String identifier, String extension) {
        return settings.getResourceUrl() + type + "/" + identifier + extension;
    }
}
