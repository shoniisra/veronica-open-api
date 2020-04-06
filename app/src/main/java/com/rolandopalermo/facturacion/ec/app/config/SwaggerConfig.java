package com.rolandopalermo.facturacion.ec.app.config;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.net.HttpHeaders;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@EnableSwagger2
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    private final String AUTH_SERVER="http://localhost:8080/veronica/oauth/";
    private final String CLIENT_ID="veronica";
    private final String CLIENT_SECRET="veronica";



    private String accessTokenUri="http://localhost:8080/veronica/oauth/token";
    public static final String securitySchemaOAuth2 = "oauth2schema";
    public static final String authorizationScopeGlobal = "global";
    public static final String authorizationScopeGlobalDesc ="accessEverything";

    private AuthorizationScope[] scopes() {
        AuthorizationScope[] scopes = {
                new AuthorizationScope("read", "for read operations"),
                new AuthorizationScope("write", "for write operations"),
                new AuthorizationScope("foo", "Access foo API") };
        return scopes;
    }
    private ApiInfo apiInfo() {
        Contact contact = new Contact("ISRA.DEV", "https://www.isra.dev/", "israteneda@gmail.com");
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
                .securitySchemes(newArrayList(securitySchema(), apiKey(), apiCookieKey()))
                .securityContexts(newArrayList(securityContext()));
    }

    @Bean
    public SecurityScheme apiKey() {
        return new ApiKey(HttpHeaders.AUTHORIZATION, "apiKey", "header");
    }

    @Bean
    public SecurityScheme apiCookieKey() {
        return new ApiKey(HttpHeaders.COOKIE, "apiKey", "cookie");
    }

    private OAuth securitySchema() {

        List<AuthorizationScope> authorizationScopeList = newArrayList();
        authorizationScopeList.add(new AuthorizationScope("read", "read all"));
        authorizationScopeList.add(new AuthorizationScope("write", "access all"));

        List<GrantType> grantTypes = newArrayList();
        GrantType passwordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant(accessTokenUri);
        grantTypes.add(passwordCredentialsGrant);

        return new OAuth("oauth2", authorizationScopeList, grantTypes);
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .build();
    }



    private List<SecurityReference> defaultAuth() {

        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
        authorizationScopes[0] = new AuthorizationScope("read", "read all");
        authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
        authorizationScopes[2] = new AuthorizationScope("write", "write all");

        return Collections.singletonList(new SecurityReference("oauth2", authorizationScopes));
    }

    @Bean
    public SecurityConfiguration security() {
        return new SecurityConfiguration
                ("username", "password", "", "", "Bearer access token", ApiKeyVehicle.HEADER, HttpHeaders.AUTHORIZATION,"");
    }



   /* @Bean
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
*/
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