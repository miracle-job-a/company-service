package com.miracle.companyservice.config;

import com.miracle.companyservice.util.interceptor.AuthorizationInterceptor;
import com.miracle.companyservice.util.interceptor.GetMethodInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthorizationInterceptor authorizationInterceptor;
    private final GetMethodInterceptor getMethodInterceptor;

    @Autowired
    public WebMvcConfig(AuthorizationInterceptor authorizationInterceptor, GetMethodInterceptor getMethodInterceptor) {
        this.authorizationInterceptor = authorizationInterceptor;
        this.getMethodInterceptor = getMethodInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String baseUrl = "/v1/company";

        registry.addInterceptor(getMethodInterceptor).order(1)
                .addPathPatterns(baseUrl + "/{companyId:\\d+}/posts/{postId:\\d+}")
        ;

        registry.addInterceptor(authorizationInterceptor).order(2)
                .excludePathPatterns("/swagger-ui/**")
                .excludePathPatterns("/error")
                .excludePathPatterns("/v3/api-docs")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/errors/token")
                .excludePathPatterns(baseUrl + "/email")
                .excludePathPatterns(baseUrl + "/bno")
                .excludePathPatterns(baseUrl + "/signup")
                .excludePathPatterns(baseUrl + "/login")
                .excludePathPatterns(baseUrl + "/main")
                .excludePathPatterns(baseUrl + "/{companyId:\\d+}/posts/{postId:\\d+}/questions")
                .excludePathPatterns(baseUrl + "/{companyId:\\d+}/faqs")
                .excludePathPatterns(baseUrl + "/{companyId:\\d+}/info")
                .excludePathPatterns(baseUrl + "/{companyId:\\d+}/posts")
                .excludePathPatterns(baseUrl + "/{companyId:\\d+}")
                .excludePathPatterns(baseUrl + "/posts/search")
                .excludePathPatterns(baseUrl + "/list")
                .excludePathPatterns(baseUrl + "/{companyId:\\d+}/approval")
                .excludePathPatterns(baseUrl + "/{companyId:\\d+}/posts/{postId:\\d+}")
                .excludePathPatterns(baseUrl)
                .excludePathPatterns(baseUrl + "/posts")
                .excludePathPatterns(baseUrl + "/posts/count")
                .excludePathPatterns(baseUrl + "/posts/{postId:\\d+}")
                .excludePathPatterns(baseUrl + "/posts/{year:\\d+}/{month:\\d+}/today")
        ;
    }
}
