package com.ddbj.ld.app.core.module;

import com.ddbj.ld.data.beans.common.JsonBean;
import com.ddbj.ld.common.annotation.Module;
import com.ddbj.ld.app.config.ConfigSet;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.List;

@Module
@AllArgsConstructor
@Slf4j
public class SearchModule {

    private final ConfigSet config;
    private final ObjectMapper objectMapper;

    @Deprecated
    public void bulkInsert(
            final String index,
            final List<JsonBean> jsonBeanList
    ) {
        try (var client = new RestHighLevelClient(this.builder())) {
            var requests = new BulkRequest();
            for (JsonBean bean : jsonBeanList) {
                String identifier = bean.getIdentifier();
                String  json      = this.objectMapper.writeValueAsString(bean);

                requests.add(new IndexRequest(index).id(identifier).source(json, XContentType.JSON));
            }

            var responses = client.bulk(requests, RequestOptions.DEFAULT);

            if(responses.hasFailures()) {
                responses.forEach(res -> log.error(res.getFailureMessage()));
            }
        } catch (IOException e) {
            log.error("Bulk insert is failed.", e);
        }
    }

    public void bulkInsert(final BulkRequest requests) {
        try (var client = new RestHighLevelClient(this.builder())) {
            var responses = client.bulk(requests, RequestOptions.DEFAULT);

            if(responses.hasFailures()) {
                responses.forEach(res -> {
                    if(null != res.getFailureMessage()) {
                        log.error(res.getFailureMessage());
                    }
                });
            }
        } catch (IOException e) {
            log.error("Bulk insert is failed.", e);
        }
    }

    public void deleteIndex(final String index) {
        try (var client = new RestHighLevelClient(this.builder())) {
            var request = new DeleteIndexRequest(index);
            client.indices().delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Deleteing index is failed.", e);
        }
    }

    public boolean existsIndex(final String index) {
        try (var client = new RestHighLevelClient(this.builder())) {
            var request = new GetIndexRequest(index);
            return client.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("checking index is failed.", e);

            return false;
        }
    }

    private RestClientBuilder builder() {
        var hostname = this.config.elasticsearch.hostname;
        var port     = this.config.elasticsearch.port;
        var scheme   = this.config.elasticsearch.scheme;
        var socketTimeout = this.config.elasticsearch.socketTimeout;

        log.debug("Elasticsearch connection info(hostname: {}, port: {}, scheme: {}, socketTimeout: {})", hostname, port, scheme, socketTimeout);

        return RestClient.builder(new HttpHost(hostname, port, scheme))
                .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
                        .setSocketTimeout(socketTimeout)
                );
    }
}
