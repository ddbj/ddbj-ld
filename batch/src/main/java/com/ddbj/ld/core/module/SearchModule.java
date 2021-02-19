package com.ddbj.ld.core.module;

import com.ddbj.ld.bean.common.JsonBean;
import com.ddbj.ld.common.annotation.Module;
import com.ddbj.ld.config.ConfigSet;
import com.ddbj.ld.parser.common.JsonParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Module
@AllArgsConstructor
@Slf4j
public class SearchModule {

    private final ConfigSet config;

    private final JsonParser jsonParser;

    @Deprecated
    public void bulkInsert(String hostname, int port, String scheme, String indexName, Map<String, String> jsonMap) {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(hostname, port, scheme)));

        BulkRequest requests = new BulkRequest();

        String identifier = null;
        String json = null;

        for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
            identifier = entry.getKey();
            json = entry.getValue();

            requests.add(new IndexRequest(indexName).id(identifier).source(json, XContentType.JSON));
        }

        try {
            var responses = client.bulk(requests, RequestOptions.DEFAULT);

            if(responses.hasFailures()) {
                log.error(responses.buildFailureMessage());
            }
        } catch (IOException e) {
            log.error("idenfilier:" + identifier + ",json:" + json);
            log.error(e.getMessage());
        }

        close(client);
    }

    public void bulkInsert(String indexName, List<JsonBean> jsonBeanList) {

        String hostname = this.config.elasticsearch.hostname;
        int port        = this.config.elasticsearch.port;
        String scheme   = this.config.elasticsearch.scheme;

        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(hostname, port, scheme)));

        BulkRequest requests = new BulkRequest();

        for (JsonBean bean : jsonBeanList) {
            String identifier = bean.getIdentifier();
            String  json      = jsonParser.parse(bean);

            requests.add(new IndexRequest(indexName).id(identifier).source(json, XContentType.JSON));
        }

        try {
            var responses = client.bulk(requests, RequestOptions.DEFAULT);

            if(responses.hasFailures()) {
                responses.forEach(res -> log.error(res.getFailureMessage()));
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        close(client);
    }

    @Deprecated
    public void deleteIndex(String hostname, int port, String scheme, String indexName) {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(hostname, port, scheme)));
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);

        try {
            client.indices().delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.debug(e.getMessage());
        }

        close(client);
    }

    public void deleteIndex(String indexName) {
        String hostname = this.config.elasticsearch.hostname;
        int    port     = this.config.elasticsearch.port;
        String scheme   = this.config.elasticsearch.scheme;

        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(hostname, port, scheme)));
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);

        try {
            client.indices().delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.debug(e.getMessage());
        }

        close(client);
    }

    public boolean existsIndex(String indexName) {
        String hostname = this.config.elasticsearch.hostname;
        int    port     = this.config.elasticsearch.port;
        String scheme   = this.config.elasticsearch.scheme;

        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(hostname, port, scheme)));
        GetIndexRequest request = new GetIndexRequest(indexName);

        try {
            return client.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.debug(e.getMessage());

            return false;
        } finally {
            close(client);
        }
    }

    private void close(RestHighLevelClient client) {
        try {
            client.close();
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }
}
