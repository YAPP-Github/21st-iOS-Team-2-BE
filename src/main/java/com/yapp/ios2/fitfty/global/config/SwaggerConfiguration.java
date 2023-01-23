package com.yapp.ios2.fitfty.global.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerConfiguration {
    // SWAGGER PAGE :http://localhost:8080/swagger-ui/index.html

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yapp.ios2"))
                 .paths(PathSelectors.ant("/api/v1/**"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("FITFTY")
                .description("FITFTY API SWAGGER Document")
                .version("1.0")
                //.termsOfServiceUrl("termsOfServiceUrl")
                //.license("LICENSE")
                //.licenseUrl("")
                .build();
    }
}
