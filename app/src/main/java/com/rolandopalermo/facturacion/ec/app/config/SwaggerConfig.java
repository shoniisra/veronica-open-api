package com.rolandopalermo.facturacion.ec.app.config;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;

@EnableSwagger2
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    private final String AUTH_SERVER="http://localhost:8080/veronica/oauth/";
    private final String CLIENT_ID="veronica";
    private final String CLIENT_SECRET="veronica";
    private AuthorizationScope[] scopes() {
        AuthorizationScope[] scopes = {
                new AuthorizationScope("read", "for read operations"),
                new AuthorizationScope("write", "for write operations"),
                new AuthorizationScope("foo", "Access foo API") };
        return scopes;
    }
    private ApiInfo apiInfo() {
        Contact contact = new Contact("ISRA.DEV", "www.isra.dev", "israteneda@gmail.com");
        return new ApiInfoBuilder()
                .title("Facturación Ecuador - API REST")
                .description("Api Rest que gestiona el envio y autorización de documentos electrónicos al Servicio de Rentas Internas SRI")
                .version("1.0")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .contact(contact)
                .build();
    }

    @Bean
    public Docket v1APIConfiguration() {
        String BASE_PACKAGE = "com.rolandopalermo.facturacion.ec.app.api";
        return new Docket(
                DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(Lists.newArrayList(securityScheme()))
                .securityContexts(Lists.newArrayList(securityContext()));
    }

    @Bean
    public Docket authenticationApi() {
        final Predicate<String> OAUTH_API = PathSelectors.ant("/oauth/**");
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("OAuth 2.0 API")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(OAUTH_API)
                .build();
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .scopeSeparator(" ")
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }

    private SecurityScheme securityScheme() {
        GrantType grantType = new AuthorizationCodeGrantBuilder()
                .tokenEndpoint(new TokenEndpoint(AUTH_SERVER + "/token", "oauthtoken"))
                .tokenRequestEndpoint(
                        new TokenRequestEndpoint(AUTH_SERVER + "/authorize", CLIENT_ID, CLIENT_SECRET))
                .build();

        SecurityScheme oauth = new OAuthBuilder().name("spring_oauth")
                .grantTypes(Arrays.asList(grantType))
                .scopes(Arrays.asList(scopes()))
                .build();
        return oauth;
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(
                        Arrays.asList(new SecurityReference("spring_oauth", scopes())))
                .forPaths(PathSelectors.regex("/foos.*"))
                .build();
    }

  /*
    @Bean
    public Docket v1APIConfiguration() {
        return new Docket(

                DocumentationType.SWAGGER_2).groupName("api_v1.0").select()
                .apis(RequestHandlerSelectors.basePackage("com.rolandopalermo.facturacion.ec.app.api.v1"))
                .paths(PathSelectors.regex("/api/v1.*"))
                .build()
                .apiInfo(new ApiInfoBuilder().version("1.0")
                        .title("API 1.0")
                        .description("Documentation Veronica API")
                        .build());
}
  */
  /*
    @Bean
    public Docket v1OperationConfiguration() {
        return new Docket(
                DocumentationType.SWAGGER_2).groupName("operaciones").select()
                .apis(RequestHandlerSelectors.basePackage("com.rolandopalermo.facturacion.ec.app.api"))
                .paths(PathSelectors.regex("/operaciones.*"))
                .build()
                .apiInfo(new ApiInfoBuilder().version("1.0")
                        .title("Operaciones")
                        .description("Documentation Veronica API")
                        .build());
    }
   */

}