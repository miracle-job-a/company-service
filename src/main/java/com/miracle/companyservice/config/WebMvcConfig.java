package com.miracle.companyservice.config;

import com.miracle.companyservice.util.interceptor.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthorizationInterceptor authorizationInterceptor;

    @Autowired
    public WebMvcConfig(AuthorizationInterceptor authorizationInterceptor) {
        this.authorizationInterceptor = authorizationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String baseUrl = "/v1/company";

        registry.addInterceptor(authorizationInterceptor)
                .excludePathPatterns("/swagger-ui/index")
                .excludePathPatterns(baseUrl + "/email")
                .excludePathPatterns(baseUrl + "/bno")
                .excludePathPatterns(baseUrl + "/signup")
                .excludePathPatterns(baseUrl + "/login")
                .excludePathPatterns(baseUrl + "/main")
                .excludePathPatterns(baseUrl + "/{companyId:\\d+}/posts/{postId:\\d+}/questions")
                .excludePathPatterns(baseUrl + "/{companyId:\\d+}/faqs")
                .excludePathPatterns(baseUrl + "/{companyId:\\d+}/info")
                .excludePathPatterns(baseUrl + "/{companyId:\\d+}/posts/{postId:\\d+}/latest")
                .excludePathPatterns(baseUrl + "/{companyId:\\d+}/posts/{postId:\\d+}/deadline")
                .excludePathPatterns(baseUrl + "/{companyId:\\d+}/posts/{postId:\\d+}/end")
                .excludePathPatterns(baseUrl + "/{companyId:\\d+}/posts/{postId:\\d+}/open")
                .excludePathPatterns(baseUrl + "/{companyId:\\d+}")
                .excludePathPatterns(baseUrl + "/posts/search?strNum={\\d+}&endNum={\\d+}")
                .excludePathPatterns(HttpMethod.GET.name(), baseUrl + "/{companyId:\\d+}/posts/{postId:\\d+}")
                ;
    }
}
