package ddbjld.api.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import ddbjld.api.app.controller.handler.AuthInterceptor;

/**
 * Spring のDIコンテナ管理Beanのコンフィギュレーションクラス
 * 
 * @author m.tsumura
 */
@Configuration
@PropertySource(value = "classpath:ddbj-api.properties", encoding = "UTF-8")
public class ManagedBeanConfiguration implements WebMvcConfigurer {

    public final String baseUrl;

    public ManagedBeanConfiguration( @Value("${bean.client.base-url}") String endpoints_base_url ) {
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
     * @see org.springframework.web.servlet.handler.MappedInterceptor
     */
    @Bean
    public MappedInterceptor interceptor() {
    	
    	// TODO：APIのURLパスパターンを指定したほうが良いかも。
        return new MappedInterceptor( new String[] { "/**" }, authInterceptor() );
    }
    
    

    /**
     * @return REST API 用 HTTP Client
     */
    @Bean
	public RestTemplate restTemplate() {
		
    	// TODO：StandardRestClient に差し替えたい。
        RestTemplate restClient = new RestTemplate();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        restClient.setRequestFactory(requestFactory);

        return restClient;
    }
    
    
    
    /**
     * @return Thymeleaf プレーンテキストモードのテンプレートエンジン（メールテンプレート）
     * 
     * @see org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
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
        registry.addMapping("/**")
                .allowedMethods("POST", "GET", "DELETE")
                .allowedOrigins(this.baseUrl);
    }
}
