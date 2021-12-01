package com.ddbj.ld.app.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.bioproject")
public class BioProjectDataSourceConfig {
    private String driverClassName;
    private String url;
    private String username;
    private String password;

    @Bean(name="bioProjectDataSource")
    public DataSource createDataSource() {
        return DataSourceBuilder
                .create()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    @Bean(name="bioProjectJdbc")
    public JdbcTemplate createJdbcTemplate(@Qualifier("bioProjectDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}

