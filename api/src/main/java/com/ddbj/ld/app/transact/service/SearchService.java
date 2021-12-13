package com.ddbj.ld.app.transact.service;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.FileModule;
import com.ddbj.ld.app.core.module.SearchModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    private final SearchModule module;

    private final ConfigSet config;

    private final FileModule fileModule;

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

        List<LinkedHashMap<String, Object>> sameAsList = null == json.get("sameAs") ? new ArrayList<>() : (ArrayList<LinkedHashMap<String, Object>>)json.get("sameAs");
        List<LinkedHashMap<String, Object>> dbXrefsList = null == json.get("dbXrefs") ? new ArrayList<>() : (ArrayList<LinkedHashMap<String, Object>>)json.get("dbXrefs");
        List<LinkedHashMap<String, Object>> downloadList = null == json.get("downloadUrl") ? new ArrayList<>() : (ArrayList<LinkedHashMap<String, Object>>)json.get("downloadUrl");

        Map<String, List<LinkedHashMap<String, Object>>> groupBySameAs = new HashMap<>();
        Map<String, List<LinkedHashMap<String, Object>>> groupByDbXrefs = new HashMap<>();

        for(var sameAs : sameAsList) {
            var sameAsType = (String)sameAs.get("type");

            var targetTypeList = groupBySameAs.get(sameAsType);

            if(null == targetTypeList) {
                targetTypeList = new ArrayList<>();
            }

            targetTypeList.add(sameAs);
            groupBySameAs.put(sameAsType, targetTypeList);
        }

        for(var dbXrefs : dbXrefsList) {
            var dbXrefsType = (String)dbXrefs.get("type");

            var targetTypeList = groupByDbXrefs.get(dbXrefsType);

            if(null == targetTypeList) {
                targetTypeList = new ArrayList<>();
            }

            targetTypeList.add(dbXrefs);
            groupByDbXrefs.put(dbXrefsType, targetTypeList);
        }

        var downloadUrl = new ArrayList<LinkedHashMap<String, Object>>();

        for(var download : downloadList) {
            var url = null == download.get("url") ? null : (String)download.get("url");
            var obj = new LinkedHashMap<String, Object>();
            obj.put("type", download.get("type"));
            obj.put("name", download.get("name"));
            obj.put("url", download.get("url"));
            obj.put("ftpUrl", download.get("ftpUrl"));

            if(null != url && url.startsWith("https://ddbj.nig.ac.jp/public/ddbj_database/")) {
                var path = "/usr/local/resources/" + url.substring(url.indexOf("https://ddbj.nig.ac.jp/public/ddbj_database/"));

                obj.put("isExists", this.fileModule.exists(this.fileModule.getPath(path)));
            } else {
                obj.put("isExists", true);
            }

            downloadUrl.add(obj);
        }

        json.put("sameAs", groupBySameAs);
        json.put("dbXrefs", groupByDbXrefs);
        json.put("downloadUrl", downloadUrl);

        return json;
    }

    public LinkedHashMap<String, Object> getContext() {
        return this.module.getContext();
    }
}
