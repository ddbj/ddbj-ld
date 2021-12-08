package com.ddbj.ld.app.core.module;

import com.ddbj.ld.common.annotation.Module;
import com.ddbj.ld.app.config.ConfigSet;
import com.ddbj.ld.common.exception.DdbjException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;

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
                        this.errorInfo.append("type\tid\tmsg\n");
                    }

                    this.errorInfo.append(String.format("%s\t%s\t%s", type, id, msg));
                }
            }
        } catch (IOException e) {
            var message = "Bulk insert is failed.";
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    public void deleteIndex(final String index) {
        try (var client = new RestHighLevelClient(this.builder())) {
            var request = new DeleteIndexRequest(index);
            client.indices().delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            var message = "Deleting index is failed.";
            log.error(message, e);

            throw new DdbjException(message);
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

    public MultiGetResponse get(final MultiGetRequest requests) {
        try (var client = new RestHighLevelClient(this.builder())) {
            return client.mget(requests, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("getting data is failed.", e);

            return null;
        }
    }

    public void deleteByQuery(final DeleteByQueryRequest request) {
        try (var client = new RestHighLevelClient(this.builder())) {
            client.deleteByQuery(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            var message = "Deleting by query is failed.";
            log.error(message, e);

            throw new DdbjException(message);
        }
    }

    private RestClientBuilder builder() {
        var hostname = this.config.search.hostname;
        var port     = this.config.search.port;
        var scheme   = this.config.search.scheme;
        var socketTimeout = this.config.search.socketTimeout;

        log.debug("Elasticsearch connection info(hostname: {}, port: {}, scheme: {}, socketTimeout: {})", hostname, port, scheme, socketTimeout);

        return RestClient.builder(new HttpHost(hostname, port, scheme))
                .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
                        .setSocketTimeout(socketTimeout)
                );
    }
}
