package com.miracle.companyservice.config;

import com.fasterxml.classmate.TypeResolver;
import com.miracle.companyservice.controller.swagger.ApiDefault;
import com.miracle.companyservice.controller.swagger.ApiInterceptor;
import com.miracle.companyservice.dto.response.ErrorApiResponse;
import com.miracle.companyservice.dto.response.SuccessApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.Example;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@EnableOpenApi
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket defaultApi(TypeResolver typeResolver) {
        return defaultDocket(typeResolver, "default-API")
                .globalResponses(HttpMethod.GET, defaultResponse())
                .globalResponses(HttpMethod.POST, defaultResponse())
                .globalResponses(HttpMethod.PUT, defaultResponse())
                .globalResponses(HttpMethod.PATCH, defaultResponse())
                .globalResponses(HttpMethod.DELETE, defaultResponse())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiDefault.class))
                .build();
    }

    @Bean
    public Docket interceptedApi(TypeResolver typeResolver) {
        return defaultDocket(typeResolver, "interceptor-API")
                .globalRequestParameters(interceptorRequestParameterList())
                .globalResponses(HttpMethod.GET, interceptorResponse())
                .globalResponses(HttpMethod.POST, interceptorResponse())
                .globalResponses(HttpMethod.PUT, interceptorResponse())
                .globalResponses(HttpMethod.PATCH, interceptorResponse())
                .globalResponses(HttpMethod.DELETE, interceptorResponse())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiInterceptor.class))
                .build();
    }

    private Docket defaultDocket(TypeResolver typeResolver, String groupName) {
        return new Docket(DocumentationType.OAS_30)
                .globalRequestParameters(defaultRequestParameterList())
                .additionalModels(typeResolver.resolve(SuccessApiResponse.class))
                .additionalModels(typeResolver.resolve(ErrorApiResponse.class))
                .apiInfo(apiInfo())
                .groupName(groupName)
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

    private List<RequestParameter> defaultRequestParameterList() {
        List<RequestParameter> requestParameterList = new ArrayList<>();
        RequestParameter sessionId = new RequestParameterBuilder()
                .name("Session-Id")
                .in(ParameterType.HEADER)
                .required(Boolean.TRUE)
                .build();
        RequestParameter miracle = new RequestParameterBuilder()
                .name("Miracle")
                .in(ParameterType.HEADER)
                .required(Boolean.TRUE)
                .build();
        requestParameterList.add(sessionId);
        requestParameterList.add(miracle);

        return requestParameterList;
    }

    private List<RequestParameter> interceptorRequestParameterList() {
        List<RequestParameter> requestParameterList = new ArrayList<>();
        RequestParameter companyId = new RequestParameterBuilder()
                .name("Compnay-Id")
                .in(ParameterType.HEADER)
                .required(Boolean.TRUE)
                .build();
        RequestParameter companyEmail = new RequestParameterBuilder()
                .name("Company-Email")
                .in(ParameterType.HEADER)
                .required(Boolean.TRUE)
                .build();
        RequestParameter companyBno = new RequestParameterBuilder()
                .name("Company-Bno")
                .in(ParameterType.HEADER)
                .required(Boolean.TRUE)
                .build();

        requestParameterList.add(companyId);
        requestParameterList.add(companyEmail);
        requestParameterList.add(companyBno);
        return requestParameterList;
    }

    private List<Response> defaultResponse() {
        List<Response> defaultResponseList = new ArrayList<>();
        Response unauthorizedResponse = new ResponseBuilder()
                .code("401")
                .description("인증 실패")
                .isDefault(true)
                .examples(
                        List.of(getUnauthorizedBuild())
                ).build();

        Response serverErrorResponse = new ResponseBuilder()
                .code("500")
                .description("서버 에러")
                .isDefault(true)
                .examples(
                        List.of(getServerErrorBuild())
                ).build();

        defaultResponseList.add(unauthorizedResponse);
        defaultResponseList.add(serverErrorResponse);
        return defaultResponseList;
    }

    private static Example getServerErrorBuild() {
        return new ExampleBuilder()
                .id("1")
                .mediaType("application/json")
                .summary("서버 에러")
                .value(new ErrorApiResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "서버에 문제가 생겼습니다. 다시 시도해주세요.",
                        "500",
                        "RuntimeException"))
                .build();
    }

    private static Example getUnauthorizedBuild() {
        return new ExampleBuilder()
                .id("1")
                .mediaType("application/json")
                .summary("토큰 인증 실패")
                .value(new ErrorApiResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        "토큰 값이 일치하지 않습니다.",
                        "401",
                        "UnauthorizedTokenException"))
                .build();
    }

    private List<Response> interceptorResponse() {
        List<Response> interceptorResponseList = new ArrayList<>();
        Response notExistsCompanyResponse = new ResponseBuilder()
                .code("400")
                .description("미존재 기업")
                .isDefault(true)
                .examples(
                        List.of(new ExampleBuilder()
                                .id("1")
                                .mediaType("application/json")
                                .summary("미존재 기업")
                                .value(new SuccessApiResponse<>(
                                        HttpStatus.BAD_REQUEST.value(),
                                        "존재하지 않는 companyId 입니다.",
                                        Boolean.FALSE))
                                .build())
                ).build();

        Response unauthorizedFailResponse = new ResponseBuilder()
                .code("401")
                .description("인증 실패")
                .isDefault(true)
                .examples(
                        List.of(getUnauthorizedBuild()
                                , new ExampleBuilder()
                                        .id("2")
                                        .mediaType("application/json")
                                        .summary("회원 인증 실패")
                                        .value(new SuccessApiResponse<>(
                                                HttpStatus.UNAUTHORIZED.value(),
                                                "회원 인증에 실패하여, 정보를 요청할 수 없습니다.",
                                                Boolean.FALSE))
                                        .build(),
                                new ExampleBuilder()
                                        .id("3")
                                        .mediaType("application/json")
                                        .summary("회원 인증 값 없음")
                                        .value(new SuccessApiResponse<>(
                                                HttpStatus.UNAUTHORIZED.value(),
                                                "회원 인증 정보에 빈 값이 있습니다.",
                                                Boolean.FALSE))
                                        .build())
                ).build();

        Response forbiddenResponse = new ResponseBuilder()
                .code("403")
                .description("접근 권한 없음")
                .isDefault(true)
                .examples(
                        List.of(new ExampleBuilder()
                                .id("1")
                                .mediaType("application/json")
                                .summary("접근 권한 없음")
                                .value(new SuccessApiResponse<>(
                                        HttpStatus.FORBIDDEN.value(),
                                        "접근 권한이 없습니다.",
                                        Boolean.FALSE))
                                .build())
                ).build();

        Response serverErrorResponse = new ResponseBuilder()
                .code("500")
                .description("서버 에러")
                .isDefault(true)
                .examples(
                        List.of(getServerErrorBuild())
                ).build();

        interceptorResponseList.add(notExistsCompanyResponse);
        interceptorResponseList.add(unauthorizedFailResponse);
        interceptorResponseList.add(forbiddenResponse);
        interceptorResponseList.add(serverErrorResponse);

        return interceptorResponseList;
    }
}
