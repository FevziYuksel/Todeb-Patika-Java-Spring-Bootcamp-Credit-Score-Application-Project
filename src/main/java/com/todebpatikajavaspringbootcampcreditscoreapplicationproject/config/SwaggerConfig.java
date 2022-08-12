package com.todebpatikajavaspringbootcampcreditscoreapplicationproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {

    /**
     * localhost:8050/swagger-ui/index.html
     * Put |Bearer <TOKEN>| Bearer before Token key
     */

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.todebpatikajavaspringbootcampcreditscoreapplicationproject"))
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.regex("/api/.*"))
//                .paths(PathSelectors.any())
                .build();
    }
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Todeb Java Spring bootcamp Credit Score Application Project",
                "Credit Score Application Project",
                "1",
                "Terms of service",
                new Contact("Fevzi Yüksel", "https://github.com/FevziYuksel", "fevziyuksel1996@gmail.com"),
                "License of API",
                "API license URL",
                Collections.emptyList()
        );
    }
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("JWT", authorizationScopes));
    }


}