package ddbjld.api.app.config;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
<<<<<<< HEAD
import springfox.documentation.builders.ApiInfoBuilder;
=======

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
>>>>>>> 差分修正
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
<<<<<<< HEAD
import springfox.documentation.swagger2.annotations.EnableSwagger2;
=======
>>>>>>> 差分修正


@AllArgsConstructor
@Configuration
<<<<<<< HEAD
@EnableSwagger2
=======
>>>>>>> 差分修正
public class SwaggerConfig {

	ConfigSet config;
	
	@Bean
	public Docket doclet() {
		
		return new Docket( DocumentationType.SWAGGER_2 )
				.select()
				.apis( RequestHandlerSelectors.any() )
<<<<<<< HEAD
//				.paths( PathSelectors.regex( ".*/(view|pub)/.*" ) )
=======
				.paths( PathSelectors.regex( ".*/(view|pub)/.*" ) )
>>>>>>> 差分修正
				.build()
				.apiInfo( as( this.config.api ) );
	}
	
	
	private static ApiInfo as( ApiConfig api ) {
		
		return new ApiInfoBuilder()
				.title( api.title )
				.description( api.description )
				.version( api.version )
				.contact( new Contact(
						api.contact.name,
						api.contact.url,
						api.contact.email ) )
				.license( api.lisence.name )
				.licenseUrl( api.lisence.url )
				.termsOfServiceUrl( api.termsOfService.url )
				.build();
	}
	
<<<<<<< HEAD
}
=======
}
>>>>>>> 差分修正
