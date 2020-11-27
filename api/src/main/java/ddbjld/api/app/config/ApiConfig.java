package ddbjld.api.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:ddbj-api.properties", encoding = "UTF-8")
public class ApiConfig {
	public static class ContactInfo {
		public final String name;
		public final String url;
		public final String email;
		
		private ContactInfo( String name, String url, String email ) {
			this.name = name;
			this.url = url;
			this.email = email;
		}
	}
	
	public static class LicenseInfo {
		public final String name;
		public final String url;
		
		private LicenseInfo( String name, String url ) {
			this.name = name;
			this.url = url;
		}
	}
	
	public static class TermsOfService {
		public final String url;
		
		private TermsOfService( String url ) {
			this.url = url;
		}
	}
	
	public final String title;
	public final String description;
	public final String version;
	public final ContactInfo contact;
	public final LicenseInfo lisence;
	public final TermsOfService termsOfService;
	public final String baseUrl;
	
	public ApiConfig(
			@Value("${config.api-info.title}") String title,
			@Value("${config.api-info.description}") String description,
			@Value("${config.api-info.version}") String version,
			@Value("${config.api-info.contact.name}") String contactName,
			@Value("${config.api-info.contact.url}") String contactUrl,
			@Value("${config.api-info.contact.email}") String contactEmail,
			@Value("${config.api-info.license}") String licenseName,
			@Value("${config.api-info.license.url}") String licenseUrl,
			@Value("${config.api-info.terms-of-service.url}") String termsOfServiceUrl,
			@Value("${config.api-info.base-url}") String baseUrl) {
		
		this.title = title;
		this.description = description;
		this.version = version;
		this.contact = new ContactInfo( contactName, contactUrl, contactEmail );
		this.lisence = new LicenseInfo( licenseName, licenseUrl );
		this.termsOfService = new TermsOfService( termsOfServiceUrl );
		this.baseUrl = baseUrl;
	}
	
}