package com.ddbj.ld.app.core.module;

import com.ddbj.ld.common.annotation.Module;
import com.ddbj.ld.app.config.ConfigSet;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;

import java.io.IOException;

@Module
@AllArgsConstructor
@Slf4j
public class SearchModule {

    private final ConfigSet config;
    private final StringBuilder errorInfo = new StringBuilder();

    public void bulkInsert(final BulkRequest requests) {
        try (var client = new RestHighLevelClient(this.builder())) {
            var responses = client.bulk(requests, RequestOptions.DEFAULT);

            for(var res: responses) {
                if(res.isFailed()) {
                    var type = res.getType();
                    var id = res.getId();
                    var msg = res.getFailureMessage();

                    log.error("Registering to elasticsearch is failed.type:{},id:{},message:{}", type, id, msg);

                    if(this.errorInfo.length() == 0) {
                        this.errorInfo.append("type\tid\tmsg");
                    }

                    this.errorInfo.append("type\tid\tmsg");
                }
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

    public String getErrorInfo() {
        return this.errorInfo.toString();
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
