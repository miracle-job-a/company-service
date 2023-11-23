package com.miracle.companyservice.config;

import com.fasterxml.classmate.TypeResolver;
import com.miracle.companyservice.dto.response.ErrorApiResponse;
import com.miracle.companyservice.dto.response.SuccessApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@EnableOpenApi
@Configuration
public class SwaggerConfig {
    /**
     * http://localhost:60002/swagger-ui/index.html
     */

    @Bean
    public Docket api(TypeResolver typeResolver) {
        List<RequestParameter> requestParameterList = new ArrayList<>();
        RequestParameter sessionId = new RequestParameterBuilder()
                .name("sessionId")
                .in(ParameterType.HEADER)
                .required(Boolean.TRUE)
                .build();
        RequestParameter miracle = new RequestParameterBuilder()
                .name("miracle")
                .in(ParameterType.HEADER)
                .required(Boolean.TRUE)
                .build();
        requestParameterList.add(sessionId);
        requestParameterList.add(miracle);
        return new Docket(DocumentationType.OAS_30)
                .globalRequestParameters(requestParameterList)
                .additionalModels(typeResolver.resolve(SuccessApiResponse.class))
                .additionalModels(typeResolver.resolve(ErrorApiResponse.class))
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.miracle.companyservice.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Miracle Company Service API")
                .description("기업과 연관된 요청들을 처리하는 API")
                .version("1.0.0")
                .build();
    }
}
