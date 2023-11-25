package com.miracle.companyservice.config;

import com.miracle.companyservice.util.interceptor.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private AuthorizationInterceptor authorizationInterceptor;

    @Autowired
    public WebMvcConfig(AuthorizationInterceptor authorizationInterceptor) {
        this.authorizationInterceptor = authorizationInterceptor;
    }

   @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String baseUrl = "/v1/company";
        List<String> excludePath = new ArrayList<>();
        excludePath.add("/swagger-ui/index");
        excludePath.add(baseUrl + "/email");
        excludePath.add(baseUrl + "/bno");
        excludePath.add(baseUrl + "/signup");
        excludePath.add(baseUrl + "/login");

        //excludePath.add(baseUrl + "/*/posts/*/questions");

        //excludePath.add(baseUrl + "/main");
        excludePath.add(baseUrl + "/*/info"); // 회사 정보 조회
        // excludePath.add(baseUrl + "/posts/*"); // - 조회 (삭제랑 같은 url이라 제외해야 함)

        registry.addInterceptor(authorizationInterceptor)
                .excludePathPatterns(excludePath);
   }
}
