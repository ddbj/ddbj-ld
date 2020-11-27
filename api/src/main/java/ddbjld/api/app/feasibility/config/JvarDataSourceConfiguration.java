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
@ConfigurationProperties(prefix = "spring.datasource.jvar")
public class JvarDataSourceConfiguration {
    private String driverClassName;
    private String url;
    private String username;
    private String password;

    @Bean(name="jvarDataSource")
    public DataSource createDataSource() {
        return DataSourceBuilder
                .create()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    @Bean(name="jvarJdbc")
    public JdbcTemplate createJdbcTemplate(@Qualifier("jvarDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}

