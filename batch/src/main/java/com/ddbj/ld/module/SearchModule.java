package com.ddbj.ld.module;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Map;

@Repository
@Slf4j
public class SearchModule {
    public void bulkInsert(String hostname, int port, String scheme, String indexName, Map<String, String> jsonMap) {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(hostname, port, scheme)));
        BulkRequest request = new BulkRequest();

        for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
            request.add(new IndexRequest(indexName).id(entry.getKey()).source(entry.getValue(), XContentType.JSON));
        }

        try {
            // TODO Responseのエラーの中身を見る処理を入れる、log.info、errorで出力したい
            var response = client.bulk(request, RequestOptions.DEFAULT);

            if(response.hasFailures()) {
                log.error("");

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

    private void close(RestHighLevelClient client) {
        try {
            client.close();
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }
}
