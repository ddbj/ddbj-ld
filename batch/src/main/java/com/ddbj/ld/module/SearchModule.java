package com.ddbj.ld.module;

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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@AllArgsConstructor
@Slf4j
public class SearchModule {

    public void bulkInsert(String hostname, int port, String scheme, String indexName, Map<String, String> jsonMap) {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(hostname, port, scheme)));
        BulkRequest requests = new BulkRequest();

        for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
            requests.add(new IndexRequest(indexName).id(entry.getKey()).source(entry.getValue(), XContentType.JSON));
        }

        try {
            var responses = client.bulk(requests, RequestOptions.DEFAULT);

            if(responses.hasFailures()) {
                log.error(responses.buildFailureMessage());
            }
        } catch (IOException e) {
            log.debug(e.getMessage());
        }

        close(client);
    }

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

    public boolean existsIndex(String hostname, int port, String scheme, String indexName) {
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
