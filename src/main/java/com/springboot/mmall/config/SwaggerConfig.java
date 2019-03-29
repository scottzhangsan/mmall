package com.springboot.mmall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket createDocketApi(){
		return new Docket(DocumentationType.SWAGGER_2)
				   .pathMapping("/")
				   .select()
				   .apis(RequestHandlerSelectors.basePackage("com.springboot.mmall"))
				   .paths(PathSelectors.any())
				   .build().apiInfo(new ApiInfoBuilder()
						   .title("mmall interface swagger")
						   .description("learn Swagger ")
						    .version("9.0")
						     .contact(new Contact("soctt", "mmall.com", "text@qq.com"))
						     .license("License")
						     .licenseUrl("www.baidu.com")
						     .build()) ;
	}

}
