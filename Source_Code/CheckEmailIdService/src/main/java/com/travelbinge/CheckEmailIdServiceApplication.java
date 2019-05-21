package com.travelbinge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class CheckEmailIdServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckEmailIdServiceApplication.class, args);
	}

	
	ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Check If Email Already in use service in Spring-Boot 2")
				.description(
						"REST API for checking If Email Id is already in use or not ")
				.termsOfServiceUrl("").version("1").contact(new Contact("Surya Singh", "https://github.com/singhsurya1012/TravelBinge", "https://github.com/singhsurya1012")).build();
	}
	
	
	@Bean
	public Docket configureControllerPackageAndConvertors() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.travelbinge")).build()
				.apiInfo(apiInfo());
	}

}
