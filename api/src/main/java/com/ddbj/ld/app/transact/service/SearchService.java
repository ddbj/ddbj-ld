package com.ddbj.ld.app.transact.service;

import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.app.core.module.FileModule;
import com.ddbj.ld.app.core.module.SearchModule;
import com.ddbj.ld.common.constant.ApiMethod;
import com.ddbj.ld.common.utility.api.RestClient;
import com.ddbj.ld.data.bean.GroupByDBXrefsInfo;
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

    private final static String DOWNLOAD_PREFIX = "https://ddbj.nig.ac.jp/public/ddbj_database/";

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

        var sameAsList = null == json.get("sameAs") ? new ArrayList<LinkedHashMap<String, Object>>() : (ArrayList<LinkedHashMap<String, Object>>)json.get("sameAs");
        var dbXrefsList = null == json.get("dbXrefs") ? new ArrayList<LinkedHashMap<String, Object>>() : (ArrayList<LinkedHashMap<String, Object>>)json.get("dbXrefs");
        var downloadList = null == json.get("downloadUrl") ? new ArrayList<LinkedHashMap<String, Object>>() : (ArrayList<LinkedHashMap<String, Object>>)json.get("downloadUrl");

        var groupBySameAs = new HashMap<String, List<LinkedHashMap<String, Object>>>();
        var groupByDbXrefs = new HashMap<String, GroupByDBXrefsInfo>();

        for(var sameAs : sameAsList) {
            var sameAsType = (String)sameAs.get("type");

            var targetTypeList = groupBySameAs.get(sameAsType);

            if(null == targetTypeList) {
                targetTypeList = new ArrayList<>();
            }

            targetTypeList.add(sameAs);
            groupBySameAs.put(sameAsType, targetTypeList);
        }

        var searchURL = this.config.api.baseUrl + "/resource/" + type + "/" + identifier + ".json";

        json.put("searchURL", searchURL);

        for(var dbXref : dbXrefsList) {
            var dbXrefsType = (String)dbXref.get("type");
            var info = (GroupByDBXrefsInfo)groupByDbXrefs.get(dbXrefsType);

            if (null == info) {
                info = new GroupByDBXrefsInfo(
                        dbXrefsType,
                        new ArrayList<>(),
                        false
                );
            }

            var dbXrefs = info.getDbXrefs();

            if(dbXrefs.size() < 10) {
                dbXrefs.add(dbXref);
                info.setDbXrefs(dbXrefs);
            } else {
                info.setHasMore(true);
            }

            groupByDbXrefs.put(dbXrefsType, info);
        }

        var downloadUrl = new ArrayList<LinkedHashMap<String, Object>>();
        var client = new RestClient(); // publicationのところで再利用する

        for(var download : downloadList) {
            var url = null == download.get("url") ? null : (String)download.get("url");
            var obj = new LinkedHashMap<String, Object>();
            obj.put("type", download.get("type"));
            obj.put("name", download.get("name"));
            obj.put("url", download.get("url"));
            obj.put("ftpUrl", download.get("ftpUrl"));

            var isExists = true;

            if(null != url && url.startsWith(DOWNLOAD_PREFIX)) {
                var path = this.config.file.path.RESORUCES + "/" + url.substring(url.indexOf(DOWNLOAD_PREFIX) + DOWNLOAD_PREFIX.length());

                isExists = this.fileModule.exists(this.fileModule.getPath(path));

            } else if (null != url) {
                var api = client.exchange(url, ApiMethod.HEAD, null, null);

                isExists = api.response.is2xxSuccessful();
            }

            obj.put("isExists", isExists);

            downloadUrl.add(obj);
        }

        json.put("sameAs", groupBySameAs);
        json.put("dbXrefs", groupByDbXrefs);
        json.put("downloadUrl", downloadUrl);

        if(type.equals("bioproject")) {
            var properties = (LinkedHashMap<String, Object>)json.get("properties");
            var project = (LinkedHashMap<String, Object>)properties.get("Project");
            var projectProject = (LinkedHashMap<String, Object>)project.get("Project");
            var projectDescr = (LinkedHashMap<String, Object>)projectProject.get("ProjectDescr");
            var publicationList = null == projectDescr || null == projectDescr.get("Publication") ? new ArrayList<LinkedHashMap<String, Object>>() : (ArrayList<LinkedHashMap<String, Object>>)projectDescr.get("Publication");

            var publication = new ArrayList<LinkedHashMap<String, String>>();
            json.put("hasMorePublication", false);

            for (var p : publicationList) {

                if(publication.size() == 10) {
                    json.put("hasMorePublication", true);

                    continue;
                }

                var info = new LinkedHashMap<String, String>();

                var id = (String)p.get("id");
                var structuredCitation = (LinkedHashMap<String, String>)p.get("StructuredCitation");
                var title = null == structuredCitation || null == structuredCitation.get("Title") ? id : structuredCitation.get("Title");
                var url = "https://pubmed.ncbi.nlm.nih.gov/" + id;

                var api = client.exchange(url, ApiMethod.HEAD, null, null);

                if(api.response.not2xxSuccessful()) {
                    continue;
                }

                info.put("title", title);
                info.put("url", url);

                publication.add(info);
            }

            json.put("publication", publication);
        }

        return json;
    }

    public LinkedHashMap<String, Object> getContext() {
        return this.module.getContext();
    }
}
