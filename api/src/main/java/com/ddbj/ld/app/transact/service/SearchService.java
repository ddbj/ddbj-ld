package com.ddbj.ld.app.transact.service;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.SearchModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;

/**
 * 公開データ検索機能のサービスクラス.
 *
 * @author m.tsumura
 *
 **/
@Service
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
@Slf4j
public class SearchService {

    private SearchModule module;

    private ConfigSet config;

    public LinkedHashMap<String, Object> getJsonData(final String type, final String identifier) {
        // Elasticsearchからデータを取得
        var json = this.module.get(type, identifier);

        // FIXME suppressed対応する

        return json;
    }

    public LinkedHashMap<String, Object> getJsonldData(final String type, final String identifier) {
        // Elasticsearchからデータを取得
        var json = this.module.get(type, identifier);

        // JsonLDを作成
        LinkedHashMap<String, Object> jsonLd = new LinkedHashMap<>();
        jsonLd.put("@graph", json);
        jsonLd.put("@context", this.config.jsonLd.url);

        // FIXME suppressed対応する

        return jsonLd;
    }

    public LinkedHashMap<String, Object> getHtmlData(final String type, final String identifier) {
        // Elasticsearchからデータを取得
        var json = this.module.get(type, identifier);

        // FIXME suppressed対応する

        return json;
    }
}
