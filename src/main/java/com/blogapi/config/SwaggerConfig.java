package com.blogapi.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	public static final String AUTHORIZATION_HEADER = "Authorization";

	private ApiKey apiKey() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}

	private List<SecurityContext> securityContexts() {
		return Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build());
	}

	private List<SecurityReference> securityReferences()

	{
		AuthorizationScope scopes = new AuthorizationScope("global", "access everything");
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] { scopes }));
	}

	@Bean
	public Docket api()

	{
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo()).securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKey())).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo getInfo() {
		return new ApiInfo("Bharat Blogging Application:Backend Part",
				"This Project is developed by Rahul,Prabhat,Jatin", "1.0", "terms of service",
				new Contact("Prabhat", "https://bharatblog.com", "thakurprabhat95@gmail.com"), "License of An Api",
				"License URL", Collections.EMPTY_LIST);
	};

}
