package ddbjld.api.app.feasibility.config;

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
@ConfigurationProperties(prefix = "spring.datasource.public")
public class PublicDataSourceConfiguration {
    private String driverClassName;
    private String url;
    private String username;
    private String password;

    @Bean(name="publicDataSource")
    public DataSource createDataSource() {
        return DataSourceBuilder
                .create()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    @Bean(name="publicJdbc")
    public JdbcTemplate createJdbcTemplate(@Qualifier("publicDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}

