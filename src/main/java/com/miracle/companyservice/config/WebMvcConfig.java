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

/*   @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String baseUrl = "/v1/company";
        List<String> excludePath = new ArrayList<>();
        excludePath.add("/swagger-ui/index");
        excludePath.add(baseUrl + "/email");
        excludePath.add(baseUrl + "/bno");
        excludePath.add(baseUrl + "/signup");
        excludePath.add(baseUrl + "/login");
        excludePath.add(baseUrl + "/post/main");*/

        //excludePath.add(baseUrl + "/*/post/*/questions");
    /*    registry.addInterceptor(authorizationInterceptor)
                .excludePathPatterns(excludePath);
    }*/
}
