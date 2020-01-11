package com.ddbj.ld.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.List;

@Slf4j
public class ElasticSearchDao {
    public void bulkInsert(String hostname, int port, String scheme, List<?> list, String indexName) { ;
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(hostname, port, scheme)));

        BulkProcessor processor =  BulkProcessor.builder((request, bulkListener) ->
                client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener), new BulkProcessor.Listener() {

            @Override
            public void beforeBulk(long l, BulkRequest bulkRequest) {
                log.debug("bulkRequest = " + bulkRequest.numberOfActions());
            }

            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {
                log.debug(throwable.getMessage());
            }

            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, BulkResponse bulkResponse) {
                log.debug("bulkResponse = " + bulkResponse.hasFailures() + " " + bulkResponse.buildFailureMessage());
            }
        }).setBulkActions(20)
                .setBulkSize(new ByteSizeValue(3, ByteSizeUnit.GB))
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                .setConcurrentRequests(0)
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();

        list.forEach(bean -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT);

                String json = mapper.writeValueAsString(bean);

                processor.add(new IndexRequest(indexName).source(json, XContentType.JSON));
            } catch (IOException e) {
                log.debug(e.getMessage());
            }
        });
    }
}
