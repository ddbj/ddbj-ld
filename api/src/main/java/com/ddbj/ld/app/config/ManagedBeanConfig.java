package com.ddbj.ld.app.config;

import com.ddbj.ld.app.controller.handler.AuthInterceptor;
import com.ddbj.ld.common.utility.JsonMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Spring のDIコンテナ管理Beanのコンフィギュレーションクラス
 * 
 * @author m.tsumura
 */
@Configuration
@PropertySource(value = "classpath:ddbj-api.properties", encoding = "UTF-8")
@EnableAsync
public class ManagedBeanConfig implements WebMvcConfigurer {

    public final String baseUrl;

    public ManagedBeanConfig( @Value("${bean.client.base-url}") String endpoints_base_url ) {
        this.baseUrl = endpoints_base_url;
    }

    /**
     * @return 共通の認証処理インターセプタ
     */
    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }
    
    /**
     * @return 共通の認証処理インターセプタのURLマッピング
     * @see MappedInterceptor
     */
    @Bean
    public MappedInterceptor interceptor() {

    	// TODO：APIのURLパスパターンを指定したほうが良いかも。
        return new MappedInterceptor( new String[] { "/**" }, authInterceptor() );
    }

    /**
     * @return Thymeleaf プレーンテキストモードのテンプレートエンジン（メールテンプレート）
     *
     * @see ClassLoaderTemplateResolver
     */
    @Bean
    public SpringTemplateEngine springTemplateEngine() {
        var resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode( TemplateMode.HTML );
        resolver.setPrefix( "templates/" ); // src/mail/resources/templates
        resolver.setSuffix( ".html" );
        resolver.setCharacterEncoding( "UTF-8" );
        resolver.setCacheable( true );

        var springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setTemplateResolver( resolver );

        return springTemplateEngine;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // FIXME 許可するURLを詳細化する
        registry.addMapping("/**")
                .allowedMethods("POST", "GET", "DELETE")
                .allowedOrigins(this.baseUrl);
    }

    // FIXME 設定値は要調整
    @Bean
    public TaskExecutor taskExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        // スレッド値の初期値
        executor.setCorePoolSize(10);
        //キューの上限値
        executor.setQueueCapacity(100);
        //キューがマックスになったときにスレッドの数をいくつまで増やすか
        executor.setMaxPoolSize(100);
        // 初期化
        executor.initialize();

        return executor;
    }

    @Bean
    public LinkedHashMap<String, Object> context() {
        var resource = new ClassPathResource("schema/context.jsonld");

        try (var reader = new BufferedReader(new FileReader(resource.getFile()))) {

            var sb = new StringBuilder();
            var line = "";

            while ((line = reader.readLine()) != null){
                sb.append(line);
                sb.append("\n");
            }

            return JsonMapper.parse(sb.toString(), new TypeReference<>() {});

        } catch (IOException e) {
            return null;
        }
    }
}
