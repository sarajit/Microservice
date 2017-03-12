package oauth.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "oauth.conroller" })
@PropertySource("classpath:app.properties")
public class WebConfig extends WebMvcConfigurerAdapter {
 
    @Bean
    public static PropertySourcesPlaceholderConfigurer 
      propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
 
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
 
    @Override
    public void configureDefaultServletHandling(
      DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
 
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
 
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/home.html");
    }
    
    @Configuration 
	@EnableOAuth2Client
    protected static class ResourceConfiguration {
     
        @Value("${accessTokenUri}")
        private String accessTokenUri;
     
        @Value("${userAuthorizationUri}")
        private String userAuthorizationUri;
     
        @Value("${clientID}")
        private String clientID;
     
        @Value("${clientSecret}")
        private String clientSecret;
     
        @Bean
        public OAuth2ProtectedResourceDetails reddit() {
        	ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();

        	resourceDetails.setId("reddit");
        	resourceDetails.setClientId(clientID);
        	resourceDetails.setClientSecret(clientSecret);
        	resourceDetails.setAccessTokenUri(accessTokenUri);
            //details.setUserAuthorizationUri(userAuthorizationUri);
        	resourceDetails.setTokenName("token");
            resourceDetails.setScope(Arrays.asList("sarp_res"));
            //resourceDetails.setPreEstablishedRedirectUri("http://localhost:8080/OAuth-test-web/login");
           // resourceDetails.setUseCurrentUri(false);
            return resourceDetails;
        }
     
        @Bean
        public OAuth2RestTemplate redditRestTemplate() {
            OAuth2RestTemplate template = new OAuth2RestTemplate(reddit());
             return template;
        }
     
    }
}
