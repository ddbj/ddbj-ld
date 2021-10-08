package com.ddbj.ld.app.config;

import com.ddbj.ld.common.utility.UrlBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:ddbj-api.properties", encoding = "UTF-8")
public class ValidationConfig {
    public static class Endpoints {
        public final String VALIDATE;
        public final String RESULT;
        public final String STATUS;
        public final String METADATA_JSON;

        private Endpoints(
                final String baseUrl,
                final String validate,
                final String result,
                final String status,
                final String metadataJson) {
            this.VALIDATE      = UrlBuilder.url( baseUrl ).path( validate ).build();
            this.RESULT        = UrlBuilder.url( baseUrl ).path( result ).build();
            this.STATUS        = UrlBuilder.url( baseUrl ).path( status ).build();
            this.METADATA_JSON = UrlBuilder.url( baseUrl ).path( metadataJson ).build();
        }
    }

    public final Endpoints endpoints;

    public ValidationConfig(
            // Validation エンドポイント設定
            @Value( "${validation.endpoints.base-url}"             ) String validation_endpoints_base_url,
            @Value( "${validation.endpoints.path.validate}"      ) String validation_endpoints_validate,
            @Value( "${validation.endpoints.path.result}"    ) String validation_endpoints_result,
            @Value( "${validation.endpoints.path.status}"     ) String validation_endpoints_status,
            @Value( "${validation.endpoints.path.metadata-json}"     ) String validation_endpoints_metadata_json
    ) {
        this.endpoints = new Endpoints(
                validation_endpoints_base_url,
                validation_endpoints_validate,
                validation_endpoints_result,
                validation_endpoints_status,
                validation_endpoints_metadata_json
        );
    }
}
